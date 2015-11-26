package com.fansz.members.api.model;

/**
 * Created by allan on 15/11/26.
 */
public class VerifyCode {

    private String verifyCode;

    private Long createTime;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
