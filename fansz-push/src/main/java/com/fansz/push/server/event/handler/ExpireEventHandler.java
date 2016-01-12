package com.fansz.push.server.event.handler;

import com.fansz.push.sender.model.MessageModel;
import com.fansz.push.server.event.BusinessEvent;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.event.IEventHandler;
import org.springframework.stereotype.Component;

/**
 * Redis中的消息超过有效期，触发该事件
 * 
 * @author Administrator
 */
@Component("expireEventHandler")
public class ExpireEventHandler implements IEventHandler {

    @Override
    public void handle(BusinessEvent event) {
        MessageModel message=(MessageModel)event.getObj();
    }

    @Override
    public EventType getEventType() {
        return EventType.EXPIRE;
    }

}
