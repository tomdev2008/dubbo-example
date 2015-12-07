package com.fansz.members.model.fandom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/5/2015.
 */
public class AddFandomParam {

    @JsonProperty("fandom_creator_sn")
    private String fandomCreatorSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("fandom_name")
    private String fandomName;

    @JsonProperty("fandom_parent_id")
    private long fandomParentId;

    @JsonProperty("fandom_avatar_url")
    private String fandomAvatarUrl;

    @JsonProperty("fandom_intro")
    private String fandomIntro;

    public String getFandomCreatorSn() {
        return fandomCreatorSn;
    }

    public void setFandomCreatorSn(String fandomCreatorSn) {
        this.fandomCreatorSn = fandomCreatorSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro;
    }
}
