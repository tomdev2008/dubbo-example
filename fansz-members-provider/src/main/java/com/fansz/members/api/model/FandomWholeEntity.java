package com.fansz.members.api.model;

/**
 * Created by root on 15-11-27.
 */
public class FandomWholeEntity {

    private Integer id;

    private String fandomName;

    private Integer fandomParentId;

    private Integer fandomAdminId;

    private Integer fandomCreatorId;

    private String fandomAvatarUrl;

    private String fandomIntro;

    private Integer followCount;

    private String isFollowed;

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
        this.fandomName = fandomName;
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
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }
}
