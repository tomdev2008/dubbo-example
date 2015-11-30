package com.fansz.members.model.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 查询登陆用户详细信息
 */
public class QueryProfileParam implements Serializable {

    private static final long serialVersionUID = 784997167533858716L;

    @JsonProperty("sn")
    private String uid;

    @JsonProperty("access_token")
    private String accessToken;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
