package com.fansz.members.api.entity;

import java.io.Serializable;

public class UserEntity implements Serializable{
    private static final long serialVersionUID = 5842245029554797173L;

    private Long userId;

    private String userAccount;

    private String passwd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }
}