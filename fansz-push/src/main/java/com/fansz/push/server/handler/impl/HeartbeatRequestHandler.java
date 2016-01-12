package com.fansz.push.server.handler.impl;

import com.fansz.constant.RequestType;
import com.fansz.push.server.model.JsonRequest;
import com.fansz.push.server.session.ClientSession;
import org.springframework.stereotype.Component;

/**
 * 心跳包处理
 * 
 * @author Administrator
 */
@Component("heartbeatRequestHandler")
public class HeartbeatRequestHandler extends AbstractRequestHandler {

    private final static String RESPONSE = "{\"header\":{\"reqId\":\"%s\",\"resType\":\"heartbeat\",\"status\":\"1\"}}";

    @Override
    public RequestType getListenerName() {
        return RequestType.HEARTBEAT;
    }

    @Override
    public void onEvent(JsonRequest request, ClientSession session) {
        session.deliver(String.format(RESPONSE, request.getHeader().getReqId()));
    }

}
