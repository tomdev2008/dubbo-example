package com.fansz.members.model.user;

/**
 * Created by allan on 15/11/26.
 */
public class QueryUserParam {
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
