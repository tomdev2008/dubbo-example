package com.fansz.members.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/8.
 */
public abstract class AbstractToken implements AccessTokenAware, Serializable {

    private static final long serialVersionUID = -3079890649588068422L;

    @JsonProperty("access_token")
    private String accessToken;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
