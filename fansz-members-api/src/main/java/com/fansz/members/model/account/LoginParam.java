package com.fansz.members.model.account;

import java.io.Serializable;

/**
 * 用户登陆传入参数
 */
public class LoginParam implements Serializable {
    private static final long serialVersionUID = 7339771245324989539L;

    private String loginname;

    private String password;

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
}
