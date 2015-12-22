package com.fansz.storage.server;

import com.alibaba.fastjson.JSON;
import com.fansz.storage.fastdfs.StorageServiceUtils;
import com.fansz.storage.model.FastDFSFile;
import com.fansz.storage.model.UploadResult;
import com.fansz.storage.tools.FileTools;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        BasicHttpResponder responder = new BasicHttpResponder(ctx.channel(), HttpHeaders.isKeepAlive(request));
        if (HttpMethod.HEAD.equals(request.getMethod()) || HttpMethod.OPTIONS.equals(request.getMethod())) {
            // HTTP HEAD请求:1、只请求资源的首部；2、检查超链接的有效性；3、检查网页是否被修改；目前主要用于支持nginx的健康检测
            // HTTP OPTIONS请求：1、获取服务器支持的HTTP请求方法。
            // 2、用来检查服务器的性能。例如：AJAX进行跨域请求时的预检，需要向另外一个域名的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全;

            responder.sendStatus(HttpResponseStatus.NO_CONTENT);
        } else {
            // 不支持GET方法
            if (HttpMethod.GET.equals(request.getMethod())) {
                responder.sendStatus(HttpResponseStatus.FORBIDDEN);
            }

            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            try {
                List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
                if (datas != null) {
                    List<UploadResult> result = new ArrayList<>();
                    for (InterfaceHttpData data : datas) {
                        if (InterfaceHttpData.HttpDataType.FileUpload.equals(data.getHttpDataType())) {
                            FileUpload file = (FileUpload) data;
                            String url = process(file);
                            UploadResult uploadResult = new UploadResult(file.getFilename(), url);
                            result.add(uploadResult);
                        }
                    }
                    if (result != null && result.size() > 0) {
                        responder.sendJson(HttpResponseStatus.OK, String.format("{\"response\": [{\"status\":\"0\",\"message\":\"Success\",\"result\":%s}]}", JSON.toJSONString(result)));
                    } else {
                        responder.sendJson(HttpResponseStatus.OK, "{\"response\": [{\"status\":\"10001\",\"message\":\"No file received\",\"result\":\"{}\"}]}");
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
            LOG.error(String.format("channel(%s) encounters error", ctx.name()), cause);
            if (ctx.channel().isActive()) {

                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.INTERNAL_SERVER_ERROR);
                ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
