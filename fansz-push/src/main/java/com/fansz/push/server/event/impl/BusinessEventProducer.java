package com.fansz.push.server.event.impl;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.fansz.push.server.event.BusinessEvent;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.session.ClientSession;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@Component("businessEventProducer")
public class BusinessEventProducer {
    private static RingBuffer<BusinessEvent> ringBuffer;

    @Resource(name = "businessEventHandler")
    private BusinessEventHandler businessEventHandler;

    @PostConstruct
    public void init() {
        Executor executor = Executors.newCachedThreadPool();
        BusinessEventFactory factory = new BusinessEventFactory();
        int bufferSize = 1024;
        Disruptor<BusinessEvent> disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.SINGLE,
                new SleepingWaitStrategy());
        disruptor.handleEventsWith(businessEventHandler);
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    public void publish(EventType eventType, Serializable obj, ClientSession session) {
        long sequence = ringBuffer.next();
        try {
            BusinessEvent event = ringBuffer.get(sequence);
            event.setEventType(eventType);
            event.setObj(obj);
            event.setSession(session);
        }
        finally {
            ringBuffer.publish(sequence);
        }
    }

}
