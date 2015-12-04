package com.fansz.members.model.relationship;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/1.
 */
public class JoinFandomParam implements Serializable {

    private static final long serialVersionUID = 4543574868180635074L;

    private String sn;

    @JsonProperty("fandom_id")
    private Long fandomId;

    @JsonProperty("access_token")
    private String accessToken;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
