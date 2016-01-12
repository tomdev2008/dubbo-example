package com.fansz.push.sender.model;

import java.io.Serializable;

public class NotifyMessageVO implements Serializable {
    private static final long serialVersionUID = -2839461265934328190L;

    private String identifier;

    private String message;

    private String messageId;

    public NotifyMessageVO(String messageId, String message, String identifier) {
        this.identifier = identifier;
        this.message = message;
        this.messageId = messageId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
