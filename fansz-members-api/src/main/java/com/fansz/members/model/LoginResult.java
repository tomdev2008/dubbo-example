package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/20.
 */
public class LoginResult implements Serializable {

    private String accessToken;

    private String refreshToken;

    private long expiresAt;//accessToken的失效时间,为距离1970年1月1日0点的ms

    private long uid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
