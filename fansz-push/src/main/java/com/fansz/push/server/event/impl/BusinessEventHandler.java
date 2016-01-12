package com.fansz.push.server.event.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import com.fansz.push.server.event.BusinessEvent;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.event.IEventHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.EventHandler;

@Component("businessEventHandler")
public class BusinessEventHandler implements EventHandler<BusinessEvent>, ApplicationContextAware {
    private Map<EventType, IEventHandler> handlerMap = new ConcurrentHashMap<EventType, IEventHandler>();

    @Override
    public void onEvent(BusinessEvent event, long sequence, boolean endOfBatch) throws Exception {
        IEventHandler handler = handlerMap.get(event.getEventType());
        if (handler != null) {
            handler.handle(event);
        }
    }

    @PostConstruct
    public void init() {
        Map<String, IEventHandler> beanMap = applicationContext.getBeansOfType(IEventHandler.class);
        if (beanMap != null) {
            for (String key : beanMap.keySet()) {
                IEventHandler handler = beanMap.get(key);
                handlerMap.put(handler.getEventType(), handler);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;
}
