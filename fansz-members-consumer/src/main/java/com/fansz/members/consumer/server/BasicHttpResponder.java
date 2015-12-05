package com.fansz.members.consumer.server;

import com.alibaba.fastjson.JSON;
import com.fansz.members.consumer.rpc.RpcInvoker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HttpResponder实现类，用于向客户端产生响应内容
 */
public class BasicHttpResponder {

    private Logger LOG = LoggerFactory.getLogger(BasicHttpResponder.class);

    private final Channel channel;

    private final boolean keepAlive;

    private final AtomicBoolean responded;

    public BasicHttpResponder(Channel channel, boolean keepAlive) {
        this.channel = channel;
        this.keepAlive = keepAlive;
        responded = new AtomicBoolean(false);
    }

    public void sendJson(HttpResponseStatus status, Object object) {
        ByteBuf buffer = null;
        try {
            String content = object instanceof String ? (String) object : JSON.toJSONString(object);
            buffer = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
            sendContent(status, buffer, "application/json", new HashMap<String, String>());
        } catch (Exception e) {
            throw e;
        } finally {
            if (buffer != null) {
                buffer.release();
            }
        }
    }

    public void sendContent(HttpResponseStatus status, ByteBuf content, String contentType,
                            Map<String, String> headers) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);

        setCustomHeaders(response, headers);

        if (content != null) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            response.content().writeBytes(content);
        } else {
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, 0);
        }

        boolean responseKeepAlive = setResponseKeepAlive(response);
        ChannelFuture future = channel.writeAndFlush(response);
        if (!responseKeepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
    public void sendStatus(HttpResponseStatus status) {
        sendContent(status, null, null, new HashMap<String, String>());
    }

    public boolean isCommitted() {
        return responded.get();
    }

    /**
     * 设置Http Header
     *
     * @param response
     * @param headers
     */
    private void setCustomHeaders(HttpResponse response, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                response.headers().set(entry.getKey(), entry.getValue());
            }
        }
    }

    private boolean setResponseKeepAlive(HttpResponse response) {
        boolean closeConn = HttpHeaderValues.CLOSE.equalsIgnoreCase(response.headers().get(HttpHeaderNames.CONNECTION));
        boolean responseKeepAlive = this.keepAlive && !closeConn;

        if (responseKeepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }

        return responseKeepAlive;
    }

    /**
     * HTTP请求路由处理类，将HTTP请求分发给对应的Handler进行处理
     */
    @Component
    @ChannelHandler.Sharable
    public static class HttpRequestRouter extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Resource(name = "dubboInvoker")
        private RpcInvoker rpcInvoker;

        private static final Logger LOG = LoggerFactory.getLogger(HttpRequestRouter.class);

        public HttpRequestRouter() {

        }


        /**
         * 处理接收到的HTTP请求，并进行分发
         *
         * @param ctx
         * @param request HTTP请求对象
         * @throws Exception
         */
        @Override
        public void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            BasicHttpResponder responder = new BasicHttpResponder(ctx.channel(), HttpHeaderUtil.isKeepAlive(request));
            if (HttpMethod.HEAD.equals(request.method()) || HttpMethod.OPTIONS.equals(request.method())) {
                // HTTP HEAD请求:1、只请求资源的首部；2、检查超链接的有效性；3、检查网页是否被修改；目前主要用于支持nginx的健康检测
                // HTTP OPTIONS请求：1、获取服务器支持的HTTP请求方法。
                // 2、用来检查服务器的性能。例如：AJAX进行跨域请求时的预检，需要向另外一个域名的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全;

                responder.sendStatus(HttpResponseStatus.NO_CONTENT);
            } else {
                // 不支持GET方法
                if (request.method().equals(HttpMethod.GET)) {
                    responder.sendStatus(HttpResponseStatus.FORBIDDEN);
                }

                String url = URI.create(request.uri()).normalize().toString();
                String body = request.content().toString(Charset.forName("UTF-8"));
                String response = rpcInvoker.invoke(url, body);
                responder.sendJson(HttpResponseStatus.OK, response);
            }
        }


        /**
         * 异常时，执行该方法，返回错误信息并关闭连接
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if (ctx.channel() != null) {
                LOG.error(String.format("channel(%s) encounters error", ctx.channel().id().asShortText()), cause);
                if (ctx.channel().isActive()) {

                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.INTERNAL_SERVER_ERROR);
                    ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    /**
     * HTTP服务提供类
     *
     * @author Administrator
     */
    public static final class NettyHttpService {

        private static final Logger LOG = LoggerFactory.getLogger(NettyHttpService.class);

        private InetSocketAddress bindAddress;

        private EventLoopGroup bossGroup;

        private EventLoopGroup workerGroup;

        private final int httpChunkLimit;

        private HttpRequestRouter httpRequestRouter;

        /**
         * 初始化NettyHttpService
         *
         * @param bindAddress HTTP服务绑定的地址.
         */
        private NettyHttpService(InetSocketAddress bindAddress, int httpChunkLimit, HttpRequestRouter httpRequestRouter) {
            this.bindAddress = bindAddress;
            this.httpChunkLimit = httpChunkLimit;
            this.httpRequestRouter = httpRequestRouter;
        }


        public static Builder builder() {
            return new Builder();
        }

        public void startUp() throws Exception {
            try {
                LOG.info("Starting http service on address {}...", bindAddress);
                bossGroup = new NioEventLoopGroup();
                workerGroup = new NioEventLoopGroup();
                ServerBootstrap b = new ServerBootstrap();
                // 默认的worker线程数为 2 * numberOfCpuCores
                final LoggingHandler logging = new LoggingHandler();
                b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {

                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast("logging", logging);

                                pipeline.addLast("encoder", new HttpResponseEncoder());
                                pipeline.addLast("decoder", new HttpRequestDecoder());
                                pipeline.addLast("aggregator", new HttpObjectAggregator(httpChunkLimit));
                                // pipeline.addLast("compressor", new HttpContentCompressor());
                                pipeline.addLast("router", httpRequestRouter);
                            }

                        }).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.SO_BACKLOG, 256 * 1024)
                        .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_RCVBUF, 256 * 1024)
                        .childOption(ChannelOption.SO_SNDBUF, 256 * 1024)
                        .childOption(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                ChannelFuture f = b.bind(bindAddress).sync();
                LOG.info("Http service is running on address {}", bindAddress);
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                LOG.error("error to start http server", e);
            } finally {
                shutDown();
            }
        }

        /**
         * @return Http服务绑定的地址
         */
        public InetSocketAddress getBindAddress() {
            return bindAddress;
        }

        public void shutDown() throws Exception {
            LOG.info("Stopping service on address {}...", bindAddress);
            try {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            } finally {
            }
            LOG.info("Done stopping service on address {}", bindAddress);
        }

        /**
         * NettyHttpService构建工具类
         */
        public static class Builder {

            private static final int DEFAULT_HTTP_CHUNK_LIMIT = 150 * 1024 * 1024;// 默认最大150M

            private static final int DEFAULT_LISTEN_PORT = 80;// 默认绑定80端口

            private HttpRequestRouter httpRequestRouter;

            private String host;

            private int port;


            private int httpChunkLimit;


            // 避免外部类通过new直接创建Builder对象
            private Builder() {
                port = DEFAULT_LISTEN_PORT;
                httpChunkLimit = DEFAULT_HTTP_CHUNK_LIMIT;
            }


            /**
             * 设置监听端口，默认监听80端口
             */
            public Builder setPort(int port) {
                this.port = port;
                return this;
            }

            /**
             * 设置绑定地址，默认绑定所有地址
             *
             * @param host bindAddress for the service.
             * @return instance of {@code Builder}.
             */
            public Builder setHost(String host) {
                this.host = host;
                return this;
            }


            public Builder setHttpRequestRouter(HttpRequestRouter httpRequestRouter) {
                this.httpRequestRouter = httpRequestRouter;
                return this;
            }

            /**
             * @return {@code NettyHttpService}实例
             */
            public NettyHttpService build() {
                InetSocketAddress bindAddress;
                if (host == null) {
                    bindAddress = new InetSocketAddress(port);
                } else {
                    bindAddress = new InetSocketAddress(host, port);
                }

                return new NettyHttpService(bindAddress, httpChunkLimit, httpRequestRouter);
            }
        }
    }
}
