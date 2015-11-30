package com.fansz.sms.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/27.
 */
public class SmsMessage implements Serializable {

    private static final long serialVersionUID = 8005578263274440012L;

    private String content;

    private String mobile;

    public SmsMessage() {
    }

    public SmsMessage(String content, String mobile) {
        this.content = content;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
