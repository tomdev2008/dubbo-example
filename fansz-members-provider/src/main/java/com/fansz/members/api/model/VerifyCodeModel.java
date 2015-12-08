package com.fansz.members.api.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class VerifyCodeModel implements Serializable {

    private static final long serialVersionUID = 366016440219809214L;

    private String verifyCode;

    private Long expireTime;

    public VerifyCodeModel() {

    }

    public VerifyCodeModel(String verifyCode, Long expireTime) {
        this.verifyCode = verifyCode;
        this.expireTime = expireTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
