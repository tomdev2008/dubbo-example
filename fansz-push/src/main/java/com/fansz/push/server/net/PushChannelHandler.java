package com.fansz.push.server.net;

import com.alibaba.fastjson.JSON;
import com.fansz.pub.utils.StringTools;
import com.fansz.push.server.model.RequestHeader;
import com.fansz.push.server.model.JsonRequest;
import com.fansz.push.server.session.ClientSession;
import com.fansz.push.server.session.SessionManager;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty的Channel Handler，处理消息的接收，通道的关闭和超时等
 */
@Sharable
public class PushChannelHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(PushChannelHandler.class);

    private final AttributeKey<ClientSession> SESSION = AttributeKey.valueOf("SESSION");

    private RquestRouter rquestRouter;

    private SessionManager sessionManager;

    private void prepare(ChannelHandlerContext ctx) {
        Attribute<ClientSession> connAttr = ctx.attr(SESSION);
        ClientSession session = null;
        if (connAttr.get() == null) {
            session=sessionManager.createClientSession(ctx.channel());
            connAttr.set(session);
        } else {
            session = connAttr.get();
        }
    }

    /**
     * 读取消息，并路由到不同的listener进行处理
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (StringTools.isBlank(message))
            return;
        prepare(ctx);
        ClientSession session = ctx.attr(SESSION).get();
        // 调用节handler处理传入的消息
        try {
            JsonRequest request = JSON.parseObject(message, JsonRequest.class);
            validate(request);
            rquestRouter.process(request, session);
        } catch (Exception e) {
            logger.error(String.format("Closing connection due to error while processing message: {}", message), e);
            if (session != null) {
                session.invalidate();
            }
        }
    }

    /**
     * 参数校验：
     * 1、传入的请求头部不能为空；
     * 2、传入的AppKey不能为空；
     * 3、传入的AppKey系统中必须存在;
     *
     * @param jsonRequest
     */
    private void validate(JsonRequest jsonRequest) {
        RequestHeader header = jsonRequest.getHeader();
        if (header == null || StringTools.isBlank(header.getReqType()) || StringTools.isBlank(header.getAppKey())) {
            throw new IllegalArgumentException("传入的参数错误，请检查！");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientSession session = ctx.attr(SESSION).get();
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("channel encounter error", cause);
        ClientSession session = ctx.attr(SESSION).get();
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 处理timeout
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            ClientSession session = ctx.attr(SESSION).get();
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE || event.state() == IdleState.ALL_IDLE) {
                if (session != null) { // 关闭超时连接
                    logger.warn("PushChannelHandler: Closing channel-{} that has been idle for state-{}", session.getSessionId(), event.state());
                    session.invalidate();
                }
            }
        }

    }

    public void setRquestRouter(RquestRouter rquestRouter) {
        this.rquestRouter = rquestRouter;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
