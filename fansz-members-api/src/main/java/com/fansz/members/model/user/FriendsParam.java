package com.fansz.members.model.user;


import java.io.Serializable;

public class FriendsParam implements Serializable {

    private Long uid;

    private String accessToken;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
