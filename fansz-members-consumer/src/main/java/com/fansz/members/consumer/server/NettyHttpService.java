package com.fansz.members.consumer.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by allan on 15/12/5.
 */
public class NettyHttpService {
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
