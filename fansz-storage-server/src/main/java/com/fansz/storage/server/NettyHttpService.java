package com.fansz.storage.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.creditease.server.http.context.BasicHandlerContext;
import cn.creditease.server.http.context.HandlerContext;
import cn.creditease.server.http.handler.ExceptionHandler;
import cn.creditease.server.http.handler.HttpHandler;
import cn.creditease.server.http.interceptor.HandlerInterceptor;
import cn.creditease.server.http.route.HttpRequestRouter;
import cn.creditease.server.http.route.HttpResourceHandler;
import cn.creditease.server.http.ssl.SSLConfig;
import cn.creditease.server.http.ssl.SSLHandlerFactory;
import cn.creditease.server.http.support.URLRewriter;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 * HTTP服务提供类
 * 
 * @author Administrator
 */
public final class NettyHttpService extends AbstractIdleService {

    private static final Logger LOG = LoggerFactory.getLogger(NettyHttpService.class);

    private final HandlerContext handlerContext;

    private final HttpResourceHandler resourceHandler;

    private final SSLHandlerFactory sslHandlerFactory;

    private InetSocketAddress bindAddress;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private final int httpChunkLimit;

    /**
     * 初始化NettyHttpService
     * 
     * @param bindAddress HTTP服务绑定的地址.
     * @param urlRewriter URL重写类
     * @param httpHandlers HTTP请求处理器，对应Spring MVC中的controller
     * @param handlerHooks 拦截器
     * @param sslHandlerFactory SSL认证处理器
     * @param exceptionHandler 异常处理器
     */
    private NettyHttpService(InetSocketAddress bindAddress, URLRewriter urlRewriter,
            Iterable<? extends HttpHandler> httpHandlers, Iterable<? extends HandlerInterceptor> handlerHooks,
            SSLHandlerFactory sslHandlerFactory, ExceptionHandler exceptionHandler, int httpChunkLimit) {
        this.bindAddress = bindAddress;
        this.resourceHandler = new HttpResourceHandler(httpHandlers, handlerHooks, urlRewriter, exceptionHandler);
        this.handlerContext = new BasicHandlerContext(this.resourceHandler);
        this.sslHandlerFactory = sslHandlerFactory;
        this.httpChunkLimit = httpChunkLimit;
    }

    private boolean isSSLEnabled() {
        return this.sslHandlerFactory != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected void startUp() throws Exception {
        try {
            LOG.info("Starting http service on address {}...", bindAddress);
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            // 默认的worker线程数为 2 * numberOfCpuCores
            final LoggingHandler logging = new LoggingHandler();
            final HttpRequestRouter router = new HttpRequestRouter(resourceHandler, isSSLEnabled());
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("logging", logging);
                            if (isSSLEnabled()) {
                                // Add SSLHandler if SSL is enabled
                                pipeline.addLast("ssl", sslHandlerFactory.create());
                            }
                            pipeline.addLast("encoder", new HttpResponseEncoder());
                            pipeline.addLast("decoder", new HttpRequestDecoder());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(httpChunkLimit));
                            // pipeline.addLast("compressor", new HttpContentCompressor());
                            pipeline.addLast("router", router);
                        }

                    }).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.SO_BACKLOG, 256 * 1024)
                    .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_RCVBUF, 256 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 256 * 1024)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            resourceHandler.init(handlerContext);
            ChannelFuture f = b.bind(bindAddress).sync();
            LOG.info("Http service is running on address {}", bindAddress);
            f.channel().closeFuture().sync();
        }
        catch (Exception e) {
            LOG.error("error to start http server", e);
        }
        finally {
            shutDown();
        }
    }

    /**
     * @return Http服务绑定的地址
     */
    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }

    @Override
    protected void shutDown() throws Exception {
        LOG.info("Stopping service on address {}...", bindAddress);
        try {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        finally {
            resourceHandler.destroy(handlerContext);
        }
        LOG.info("Done stopping service on address {}", bindAddress);
    }

    /**
     * NettyHttpService构建工具类
     */
    public static class Builder {

        private static final int DEFAULT_HTTP_CHUNK_LIMIT = 150 * 1024 * 1024;// 默认最大150M

        private static final int DEFAULT_LISTEN_PORT = 80;// 默认绑定80端口

        private Iterable<? extends HttpHandler> handlers;

        private Iterable<? extends HandlerInterceptor> handlerHooks = ImmutableList.of();

        private URLRewriter urlRewriter = null;

        private String host;

        private int port;

        private SSLHandlerFactory sslHandlerFactory;

        private int httpChunkLimit;

        private ExceptionHandler exceptionHandler;

        // 避免外部类通过new直接创建Builder对象
        private Builder() {
            port = DEFAULT_LISTEN_PORT;
            sslHandlerFactory = null;
            httpChunkLimit = DEFAULT_HTTP_CHUNK_LIMIT;
            exceptionHandler = new ExceptionHandler();
        }

        /**
         * 添加HTTP请求处理类，相当于Spring MVC的Controller
         *
         * @param handlers HttpHandlers
         * @return {@code Builder}.
         */
        public Builder addHttpHandlers(Iterable<? extends HttpHandler> handlers) {
            this.handlers = handlers;
            return this;
        }

        /**
         * 设置拦截器
         */
        public Builder setHandlerInterceptors(Iterable<? extends HandlerInterceptor> handlerHooks) {
            this.handlerHooks = handlerHooks;
            return this;
        }

        /**
         * 设置URL重写处理类
         */
        public Builder setUrlRewriter(URLRewriter urlRewriter) {
            this.urlRewriter = urlRewriter;
            return this;
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

        /**
         * 提供SSL信息，启用HTTPS
         */
        public Builder enableSSL(SSLConfig sslConfig) {
            this.sslHandlerFactory = new SSLHandlerFactory(sslConfig);
            return this;
        }

        /**
         * 设置异常处理器
         * 
         * @param exceptionHandler
         * @return
         */
        public Builder setExceptionHandler(ExceptionHandler exceptionHandler) {
            Preconditions.checkNotNull(exceptionHandler, "exceptionHandler cannot be null");
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        /**
         * @return {@code NettyHttpService}实例
         */
        public NettyHttpService build() {
            InetSocketAddress bindAddress;
            if (host == null) {
                bindAddress = new InetSocketAddress(port);
            }
            else {
                bindAddress = new InetSocketAddress(host, port);
            }

            return new NettyHttpService(bindAddress, urlRewriter, handlers, handlerHooks, sslHandlerFactory,
                    exceptionHandler, httpChunkLimit);
        }
    }
}
