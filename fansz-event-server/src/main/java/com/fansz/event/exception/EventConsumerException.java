package com.fansz.event.exception;

/**
 * SMS消息线程异常
 */
public class EventConsumerException extends RuntimeException {
    public EventConsumerException(String message, Exception e) {
        super(message, e);
    }
}
