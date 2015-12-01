package com.fansz.members.model.relationship;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/1.
 */
public class JoinFandomParam implements Serializable {

    private static final long serialVersionUID = 4543574868180635074L;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("fandom_sn")
    private String fandomSn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getFandomSn() {
        return fandomSn;
    }

    public void setFandomSn(String fandomSn) {
        this.fandomSn = fandomSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
