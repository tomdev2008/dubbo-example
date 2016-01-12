package com.fansz.push.sender.model;

import java.io.Serializable;

public class MessageModel implements Serializable {
    private static final long serialVersionUID = 3327534603155321144L;

    private String messageId;

    private String payLoad;

    private long expire;// -1表示立即过期

    private long scheduledAt;// 定时发送，0表示非定时发送

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(long scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

}
