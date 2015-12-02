package com.fansz.storage.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Multimap;

/**
 * HttpResponder实现类，用于向客户端产生响应内容
 */
public class BasicHttpResponder extends AbstractHttpResponder {

    private Logger LOG = LoggerFactory.getLogger(BasicHttpResponder.class);

    private final Channel channel;

    private final boolean keepAlive;

    private final AtomicBoolean responded;

    public BasicHttpResponder(Channel channel, boolean keepAlive) {
        this.channel = channel;
        this.keepAlive = keepAlive;
        responded = new AtomicBoolean(false);
    }

    @Override
    public ChunkResponder sendChunkStart(HttpResponseStatus status, @Nullable Multimap<String, String> headers) {
        Preconditions.checkArgument(responded.compareAndSet(false, true), "Response has been already sent");
        Preconditions.checkArgument((status.code() >= 200 && status.code() < 210), "Http Chunk Failure");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);

        setCustomHeaders(response, headers);

        response.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);

        boolean responseKeepAlive = setResponseKeepAlive(response);
        channel.write(response);
        return new ChannelChunkResponder(channel, responseKeepAlive);
    }

    @Override
    public void sendContent(HttpResponseStatus status, @Nullable ByteBuf content, String contentType,
            @Nullable Multimap<String, String> headers) {
        Preconditions.checkArgument(responded.compareAndSet(false, true), "Response has been already sent");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);

        setCustomHeaders(response, headers);

        if (content != null) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            response.content().writeBytes(content);
        }
        else {
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, 0);
        }

        boolean responseKeepAlive = setResponseKeepAlive(response);
        ChannelFuture future = channel.writeAndFlush(response);
        if (!responseKeepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void sendFile(File file, @Nullable Multimap<String, String> headers) {
        Preconditions.checkArgument(responded.compareAndSet(false, true), "Response has been already sent");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

        setCustomHeaders(response, headers);
        response.headers().setLong(HttpHeaderNames.CONTENT_LENGTH, file.length());

        final boolean responseKeepAlive = setResponseKeepAlive(response);

        // Write the initial line and the header.
        channel.write(response);

        // Write the content.

        ChannelFuture sendFileFuture;
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "r");
            long fileLength = rf.length();
            sendFileFuture = channel.write(new ChunkedFile(rf, 0, fileLength, 8192), channel.newProgressivePromise());
            sendFileFuture.addListener(new ChannelProgressiveFutureListener()
            {
                @Override
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                    if (total < 0) { // total unknown
                        LOG.error("Transfer progress: " + progress);
                    }
                    else {
                        LOG.error("Transfer progress: " + progress + " / " + total);
                    }
                }

                @Override
                public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                    if (!responseKeepAlive) {
                        channel.close();
                    }
                }
            });
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public boolean isCommitted() {
        return responded.get();
    }

    /**
     * 设置Http Header
     * 
     * @param response
     * @param headers
     */
    private void setCustomHeaders(HttpResponse response, @Nullable Multimap<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, Collection<String>> entry : headers.asMap().entrySet()) {
                response.headers().set(entry.getKey(), entry.getValue());
            }
        }
    }

    private boolean setResponseKeepAlive(HttpResponse response) {
        boolean closeConn = HttpHeaderValues.CLOSE.equalsIgnoreCase(response.headers().get(HttpHeaderNames.CONNECTION));
        boolean responseKeepAlive = this.keepAlive && !closeConn;

        if (responseKeepAlive) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }

        return responseKeepAlive;
    }

}
