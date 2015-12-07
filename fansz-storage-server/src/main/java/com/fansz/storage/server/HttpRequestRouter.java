package com.fansz.storage.server;

import com.fansz.storage.fastdfs.StorageServiceUtils;
import com.fansz.storage.model.FastDFSFile;
import com.fansz.storage.tools.FileTools;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求路由处理类，将HTTP请求分发给对应的Handler进行处理
 */
@Sharable
public class HttpRequestRouter extends SimpleChannelInboundHandler<FullHttpRequest> {

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

            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            try {
                List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
                if (datas != null) {
                    for (InterfaceHttpData data : datas) {
                        if (InterfaceHttpData.HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                            FileUpload file = (FileUpload) data;
                            String url = process(file);
                            if (url !=null && url.trim().length() > 0) {
                                responder.sendJson(HttpResponseStatus.OK, String.format("{\"response\": [{\"status\":\"0\",\"message\":\"Success\",\"result\":{\"url\":\"%s\"}}]}", url));
                            } else {
                                responder.sendJson(HttpResponseStatus.OK, String.format("{\"response\": [{\"status\":\"10001\",\"message\":\"Fail\",\"result\":{\"url\":\"%s\"}}]}", ""));
                            }
                        }
                    }
                }
            } finally {
                decoder.destroy();
            }
        }
    }

    private String process(FileUpload file) {
        FastDFSFile fastDFSFile = new FastDFSFile(file.getFilename(), file.content().array(), FileTools.getExtension(file.getFilename()));
        return StorageServiceUtils.upload(fastDFSFile);
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
