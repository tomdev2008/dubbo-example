package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/22.
 */
public class RefreshResult implements Serializable {
    private static final long serialVersionUID = 407301536399332573L;

    private String accessToken;


    private long expiresAt;//accessToken的失效时间,为距离1970年1月1日0点的ms

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
