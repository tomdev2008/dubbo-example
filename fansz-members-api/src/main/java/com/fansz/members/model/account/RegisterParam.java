package com.fansz.members.model.account;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户注册参数
 */

public class RegisterParam implements Serializable {

    private static final long serialVersionUID = -4004586460484111481L;

    @Size(min=1,max=20)
    private String loginname;

    @Size(min=1,max=20)
    private String password;

    @Size(min=1,max=20)
    private String mobile;

    @Size(min=1,max=6)
    @JsonProperty("verify_code")
    private String verifyCode;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
