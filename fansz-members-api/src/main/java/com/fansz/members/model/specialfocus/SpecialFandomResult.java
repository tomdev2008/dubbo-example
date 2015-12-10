package com.fansz.members.model.specialfocus;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFandomResult {
    @JsonProperty("")
    private Long fandomId;
    @JsonProperty("")
    private String fandomName;
    @JsonProperty("")
    private Long fandomParentId;
    @JsonProperty("")
    private String fandomAdminSn;
    @JsonProperty("")
    private String fandomCreatorSn;
    @JsonProperty("")
    private String fandomAvatarUrl;
    @JsonProperty("")
    private String fandomIntro;
    @JsonProperty("")
    private Date fandomCreateTime;
    @JsonProperty("")
    private Long postionTag;

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public String getFandomAdminSn() {
        return fandomAdminSn;
    }

    public void setFandomAdminSn(String fandomAdminSn) {
        this.fandomAdminSn = fandomAdminSn;
    }

    public String getFandomCreatorSn() {
        return fandomCreatorSn;
    }

    public void setFandomCreatorSn(String fandomCreatorSn) {
        this.fandomCreatorSn = fandomCreatorSn;
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

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }

    public Long getPostionTag() {
        return postionTag;
    }

    public void setPostionTag(Long postionTag) {
        if(this.fandomId != null) {
            this.postionTag = postionTag;
        }
    }
}
