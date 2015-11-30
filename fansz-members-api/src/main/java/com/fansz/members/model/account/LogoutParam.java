package com.fansz.members.model.account;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/30.
 */
public class LogoutParam implements Serializable{

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("sn")
    private String uid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
