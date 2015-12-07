package com.fansz.members.model.fandom;

import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户关注的fandom信息
 */
public class FandomInfoResult implements Serializable {

    private static final long serialVersionUID = -6948988720607403742L;

    private Long id;

    @JsonProperty("fandom_name")
    private String fandomName;

    @JsonProperty("fandom_parent_id")
    private Long fandomParentId;

    @JsonProperty("fandom_admin_sn")
    private String fandomAdminSn;

    @JsonProperty("fandom_create_sn")
    private String fandomCreatorSn;

    @JsonProperty("fandom_avatar_url")
    private String fandomAvatarUrl;

    @JsonProperty("fandom_intro")
    private String fandomIntro;

    @JsonProperty("fandom_create_time")
    private Date fandomCreateTime;

    @JsonProperty("follower_count")
    private int followerCount;

    @JsonProperty("post_count")
    private int postCount;

    @JsonProperty("followed")
    private int followed;

    @JsonProperty("creator")
    private UserInfoResult creator;

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

    public UserInfoResult getCreator() {
        return creator;
    }

    public void setCreator(UserInfoResult creator) {
        this.creator = creator;
    }

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
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
}
