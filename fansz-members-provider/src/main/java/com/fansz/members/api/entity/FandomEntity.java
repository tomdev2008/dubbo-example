package com.fansz.members.api.entity;

public class FandomEntity {
    private Integer id;

    private String fandomName;

    private Integer fandomParentId;

    private Integer fandomAdminId;

    private Integer fandomCreatorId;

    private String fandomAvatarUrl;

    private String fandomIntro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName == null ? null : fandomName.trim();
    }

    public Integer getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Integer fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public Integer getFandomAdminId() {
        return fandomAdminId;
    }

    public void setFandomAdminId(Integer fandomAdminId) {
        this.fandomAdminId = fandomAdminId;
    }

    public Integer getFandomCreatorId() {
        return fandomCreatorId;
    }

    public void setFandomCreatorId(Integer fandomCreatorId) {
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
}