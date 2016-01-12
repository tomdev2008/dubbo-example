package com.fansz.push.server.event;


/**
 * 对于所有耗时操作，包括访问数据库、网络、存储等，都通过disruptor构建的业务线程池进行处理，避免堵塞Netty 的IO线程
 * 
 * @author Administrator
 */
public interface IEventHandler {
    void handle(BusinessEvent event);

    EventType getEventType();
}
