package com.fansz.push.server;

import com.fansz.push.server.session.SessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动推送服务器，采用单例模式:读取spring配置文件，由Netty启动服务
 */
public class MessageServer {

    private static final Logger logger = LoggerFactory.getLogger(MessageServer.class);

    private boolean shuttingDown;

    private long startTime;

    private SessionManager sessionManager;

    private ServerChannelInitializer serverChannelInitializer;

    private int port = 5442;

    public MessageServer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * 启动消息服务器
     */
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(serverChannelInitializer).option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_RCVBUF, 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 4096)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();
            logger.debug("push server listen at port {}", port);
            // Wait until the server socket is closed.
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("shutdown push server", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    /**
     * 关闭服务器
     */
    public void stop() {
        shutdownServer();
        Thread shutdownThread = new ShutdownThread();
        shutdownThread.setDaemon(true);
        shutdownThread.start();
    }

    /**
     * 返回服务器是否已经被关闭
     */
    public boolean isShuttingDown() {
        return shuttingDown;
    }

    private void shutdownServer() {
        shuttingDown = true;
        // Close all connections
        sessionManager.closeAllSessions();
        logger.info("MessageServer stopped");
    }

    private class ShutdownThread extends Thread {
        public void run() {
            try {
                Thread.sleep(5000);
                System.exit(0);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setServerChannelInitializer(ServerChannelInitializer serverChannelInitializer) {
        this.serverChannelInitializer = serverChannelInitializer;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
