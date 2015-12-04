package com.fansz.members.api.entity;

import java.util.Date;

public class FandomEntity {
    private Long id;

    private String fandomName;

    private Long fandomParentId;

    private Long fandomAdminId;

    private Long fandomCreatorId;

    private String fandomAvatarUrl;

    private String fandomIntro;

    private Date fandomCreateTime;

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
        this.fandomName = fandomName == null ? null : fandomName.trim();
    }

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public Long getFandomAdminId() {
        return fandomAdminId;
    }

    public void setFandomAdminId(Long fandomAdminId) {
        this.fandomAdminId = fandomAdminId;
    }

    public Long getFandomCreatorId() {
        return fandomCreatorId;
    }

    public void setFandomCreatorId(Long fandomCreatorId) {
        this.fandomCreatorId = fandomCreatorId;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl == null ? null : fandomAvatarUrl.trim();
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro == null ? null : fandomIntro.trim();
    }

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }
}