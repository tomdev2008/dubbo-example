package com.fansz.push.server.event.impl;

import com.fansz.push.server.event.BusinessEvent;

import com.lmax.disruptor.EventFactory;

public class BusinessEventFactory implements EventFactory<BusinessEvent>{

    @Override
    public BusinessEvent newInstance() {
        return new BusinessEvent();
    }


}
