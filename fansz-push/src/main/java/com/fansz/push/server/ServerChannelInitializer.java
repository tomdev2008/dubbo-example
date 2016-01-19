package com.fansz.push.server;

import com.fansz.push.codec.JsonObjectDecoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final int IDEL_TIME_OUT = 300;

    private static final int READ_IDEL_TIME_OUT = 300;

    private static final int WRITE_IDEL_TIME_OUT = 300;

    private LoggingHandler loggingHandler = new LoggingHandler();

    private ChannelHandler notifyChannelHandler = null;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("logging", loggingHandler);
        ch.pipeline().addLast("idleStateHandler",
                new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, IDEL_TIME_OUT));
        // 编码器
        ch.pipeline().addLast("jsonDecoder", new JsonObjectDecoder());
        ch.pipeline().addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast("notifyChannelHandler", notifyChannelHandler);

    }

}
