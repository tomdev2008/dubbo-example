package com.fansz.members.model.profile;


import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class FriendsParam extends PageParam implements Serializable {

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
