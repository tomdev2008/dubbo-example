package com.fansz.service.api.entity;

import java.util.Date;

public class FandomEntity {
    private Long id;

    private String fandomName;

    private Long fandomParentId;

    private String fandomAdminSn;

    private String fandomCreatorSn;

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