package com.fansz.auth.model;

import java.io.Serializable;

/**
 * Created by allan on 15/12/30.
 */
public class SessionQueryParam implements Serializable {

    private static final long serialVersionUID = -1219643595537525637L;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
