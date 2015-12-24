package com.fansz.service.model.account;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户注册传入参数
 */

public class RegisterParam implements Serializable {

    private static final long serialVersionUID = -4004586460484111481L;

    @NotBlank
    @Size(min = 1, max = 20)
    private String loginname;

    @NotBlank
    @Size(min = 1)
    private String password;

    @NotBlank
    @Size(min = 1, max = 20)
    private String mobile;

    @NotBlank
    @Size(min = 1, max = 6)
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
