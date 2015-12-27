package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/7.
 */
public class GetPostFandomInfo implements Serializable{

    private static final long serialVersionUID = -2586811055652415053L;

    @JSONField(name="fandom_id")
    private Long fandomId;

    @JSONField(name="fandom_name")
    private String fandomName;

    @JSONField(name="fandom_parent_id")
    private String fandomParentId;

    @JSONField(name="fandom_creator_id")
    private String fandomCreatorId;

    @JSONField(name="fandom_avatar_url")
    private String fandomAvatarUrl;

    @JSONField(name="fandom_intro")
    private String fandomIntro;

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

    public String getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(String fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public String getFandomCreatorId() {
        return fandomCreatorId;
    }

    public void setFandomCreatorId(String fandomCreatorId) {
        this.fandomCreatorId = fandomCreatorId;
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
