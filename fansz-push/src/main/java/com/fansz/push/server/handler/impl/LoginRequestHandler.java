package com.fansz.push.server.handler.impl;

import javax.annotation.Resource;

import com.fansz.push.server.session.ClientSession;
import org.springframework.stereotype.Component;

import com.fansz.constant.RequestType;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.event.impl.BusinessEventProducer;
import com.fansz.push.server.model.JsonRequest;

/**
 * 用户登陆
 * @author Administrator
 *
 */
@Component("loginRequestHandler")
public class LoginRequestHandler extends AbstractRequestHandler {
    
    @Resource(name = "businessEventProducer")
    protected BusinessEventProducer businessEventProducer;

    @Override
    public RequestType getListenerName() {
        return RequestType.LOGIN;
    }

    @Override
    public void onEvent(JsonRequest request, ClientSession session) {
        businessEventProducer.publish(EventType.LOGIN, request, session);
    }

}
