package com.fansz.members.model.profile;


import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class FriendsQueryParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 1170651723025637518L;

    private String sn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
