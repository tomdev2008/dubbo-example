package com.fansz.members.model.param;

/**
 * Created by xuzhen on 15/11/3.
 */

public class ResetPasswordParam {

    private String mobile;

    private String password;

    private String identifyCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }
}
