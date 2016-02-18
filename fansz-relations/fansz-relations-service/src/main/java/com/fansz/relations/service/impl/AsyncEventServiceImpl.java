package com.fansz.relations.service.impl;

import com.fansz.event.model.AddFriendEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.relations.model.OpRequestParam;
import com.fansz.relations.service.AsyncEventService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by allan on 16/2/1.
 */
@Component("asyncEventService")
public class AsyncEventServiceImpl implements AsyncEventService {

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Override
    public void addFriend(OpRequestParam opRequestParam) {
        AddFriendEvent addFriendEvent = new AddFriendEvent();
        addFriendEvent.setMyMemberSn(opRequestParam.getCurrentSn());
        addFriendEvent.setFriendMemberSn(opRequestParam.getFriendMemberSn());
        eventProducer.produce(AsyncEventType.ADD_FRIEND, addFriendEvent);
    }
}
