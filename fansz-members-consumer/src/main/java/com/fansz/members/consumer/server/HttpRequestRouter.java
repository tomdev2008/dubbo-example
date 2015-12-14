package com.fansz.members.consumer.server;

import com.fansz.members.consumer.rpc.RpcInvoker;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * Created by allan on 15/12/5.
 */
@Component
@Sharable
public class HttpRequestRouter extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String ERROR = "{\"header\": {},\"response\": [{\"method\": \"\",\"status\": \"10008\",\"message\": \"Error parameter format\",\"result\": {}}]}";

    @Resource(name = "dynamicDubboInvoker")
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
                BasicHttpResponder responder = new BasicHttpResponder(ctx.channel(), false);
                responder.sendJson(HttpResponseStatus.OK, ERROR);
            }
        }
    }
}
