package com.fansz.members.model.fandom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class FandomInfoParam {

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("fandom_id")
    private long fandomId;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getFandomId() {
        return fandomId;
    }

    public void setFandomId(long fandomId) {
        this.fandomId = fandomId;
    }
}
