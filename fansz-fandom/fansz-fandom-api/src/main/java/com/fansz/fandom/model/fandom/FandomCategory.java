package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 15-12-4.
 */
public class FandomCategory implements Serializable {

    private Long id;

    @JSONField(name="fandom_name")
    private String fandomName;

    @JSONField(name="fandom_parent_id")
    private Long fandomParentId;

    @JSONField(name="fandom_avatar_url")
    private String fandomAvatarUrl;

    @JSONField(name="fandom_intro")
    private String fandomIntro;

    @JSONField(name="fandom_create_time")
    private Date fandomCreateTime;

    @JSONField(name="child")
    List<FandomInfoResult> childCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<FandomInfoResult> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<FandomInfoResult> childCategory) {
        this.childCategory = childCategory;
    }
}
