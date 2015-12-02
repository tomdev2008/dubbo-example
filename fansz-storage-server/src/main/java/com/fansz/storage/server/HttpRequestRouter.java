package com.fansz.storage.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.creditease.server.http.pipeline.HttpWorkPublisher;
import cn.creditease.server.http.response.BasicHttpResponder;
import cn.creditease.server.http.support.HandlerException;

/**
 * HTTP请求路由处理类，将HTTP请求分发给对应的Handler进行处理
 */
@Sharable
public class HttpRequestRouter extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestRouter.class);

    private final boolean enableSSL;

    private HttpWorkPublisher workBuffer;

    private HttpResourceHandler httpResourceHandler;

    public HttpRequestRouter(HttpResourceHandler httpResourceHandler, boolean enableSSL) {
        this.httpResourceHandler = httpResourceHandler;
        workBuffer = new HttpWorkPublisher();
        workBuffer.start();
        this.enableSSL = enableSSL;
    }

    /**
     * 处理HTTPS请求，如果HTTPS认证失败，将关闭连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (enableSSL) {
            final SslHandler sslHandler = ctx.pipeline().get(SslHandler.class);

            Future<Channel> handshakeFuture = sslHandler.handshakeFuture();
            handshakeFuture.addListener(new GenericFutureListener<Future<Channel>>()
            {
                @Override
                public void operationComplete(Future<Channel> channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        channelFuture.get().close();
                    }
                }
            });
        }
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
        if (HttpMethod.HEAD.equals(request.method()) || HttpMethod.OPTIONS.equals(request.method())) {
            // HTTP HEAD请求:1、只请求资源的首部；2、检查超链接的有效性；3、检查网页是否被修改；目前主要用于支持nginx的健康检测
            // HTTP OPTIONS请求：1、获取服务器支持的HTTP请求方法。
            // 2、用来检查服务器的性能。例如：AJAX进行跨域请求时的预检，需要向另外一个域名的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全;
            BasicHttpResponder responder = new BasicHttpResponder(ctx.channel(), HttpHeaderUtil.isKeepAlive(request));
            responder.sendStatus(HttpResponseStatus.NO_CONTENT);
        }
        else {
            String connectionId = ctx.channel().id().asShortText();
            HttpMethodInfo endpoint = httpResourceHandler.getDestinationMethod(request,
                    new BasicHttpResponder(ctx.channel(), HttpHeaderUtil.isKeepAlive(request)));
            workBuffer.addWork(connectionId, endpoint);
            LOG.debug("channel {} is added to  queue", connectionId);
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
                FullHttpResponse response = null;
                if (cause instanceof HandlerException) {
                    response = ((HandlerException)cause).createFailureResponse();
                }
                else {
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.INTERNAL_SERVER_ERROR);
                }
                if (response != null) {
                    ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                }
                else {
                    ctx.channel().close();
                }
            }
        }
    }
}
