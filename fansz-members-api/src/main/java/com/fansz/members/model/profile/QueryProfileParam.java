package com.fansz.members.model.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 查询登陆用户详细信息
 */
public class QueryProfileParam implements Serializable {

    private static final long serialVersionUID = 784997167533858716L;

    private String sn;

    @JsonProperty("friend_sn")
    private String friendSn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getFriendSn() {
        return friendSn;
    }

    public void setFriendSn(String friendSn) {
        this.friendSn = friendSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
