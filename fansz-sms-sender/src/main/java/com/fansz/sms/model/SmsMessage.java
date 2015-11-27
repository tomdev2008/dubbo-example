package com.fansz.sms.model;

/**
 * Created by allan on 15/11/27.
 */
public class SmsMessage {

    private static final long serialVersionUID = 8005578263274440012L;

    private String content;

    private String mobile;

    public SmsMessage(){

    }

    public SmsMessage(String content, String mobile) {
        this.content = content;
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
