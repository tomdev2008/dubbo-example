package com.fansz.members.model.specialfocus;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFandomResult implements Serializable {

    private static final long serialVersionUID = -2947919801506650305L;

    @JsonProperty("fandom_id")
    private Long fandomId;

    @JsonProperty("fandom_name")
    private String fandomName;

    @JsonProperty("fandom_parent_id")
    private Long fandomParentId;

    @JsonProperty("fandom_admin_sn")
    private String fandomAdminSn;

    @JsonProperty("fandom_creator_sn")
    private String fandomCreatorSn;

    @JsonProperty("fandom_avatar_url")
    private String fandomAvatarUrl;

    @JsonProperty("fandom_intro")
    private String fandomIntro;

    @JsonProperty("fandom_createtime")
    private Date fandomCreateTime;

    @JsonProperty("postion_tag")
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
        if (this.fandomId != null) {
            this.postionTag = postionTag;
        }
    }
}
