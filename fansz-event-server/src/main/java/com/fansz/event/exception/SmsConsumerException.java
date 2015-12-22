package com.fansz.event.exception;

/**
 * SMS消息线程异常
 */
public class SmsConsumerException extends RuntimeException {
    public SmsConsumerException(String message, Exception e) {
        super(message, e);
    }
}
