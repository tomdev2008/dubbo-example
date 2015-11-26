package com.fansz.members.api.utils;

/**
 * 错误信息格式错误
 *
 * Created by xuzhen on 15/7/26.
 */
public class MessageFormatException extends Exception {

    public MessageFormatException() {
        super();
    }

    public MessageFormatException(String message) { super(message); };

    public MessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageFormatException(Throwable cause) {
        super(cause);
    }
}
