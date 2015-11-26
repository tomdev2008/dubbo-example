package com.fansz.members.model.account;


import java.io.Serializable;

/**
 * 重置密码参数
 */

public class ResetPasswordParam implements Serializable {

    private static final long serialVersionUID = -917012582812283556L;

    private String mobile;

    private String password;

    private String verifyCode;

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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
