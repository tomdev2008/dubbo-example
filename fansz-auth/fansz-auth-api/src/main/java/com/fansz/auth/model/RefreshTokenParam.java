package com.fansz.auth.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by allan on 15/12/30.
 */
public class RefreshTokenParam implements Serializable {
    private static final long serialVersionUID = 3995124092748745134L;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
