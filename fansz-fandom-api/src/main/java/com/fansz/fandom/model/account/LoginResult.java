package com.fansz.fandom.model.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.fandom.model.profile.UserInfoResult;

import java.io.Serializable;

/**
 * Created by allan on 15/11/20.
 */
public class LoginResult extends UserInfoResult implements Serializable {

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="refresh_token")
    private String refreshToken;

    @JSONField(name="expires_at")
    private long expiresAt;//accessToken的失效时间,为距离1970年1月1日0点的ms

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
}
