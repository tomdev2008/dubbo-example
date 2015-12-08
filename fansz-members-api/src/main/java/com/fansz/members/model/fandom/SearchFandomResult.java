package com.fansz.members.model.fandom;

import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/5.
 */
public class SearchFandomResult implements Serializable{
    private static final long serialVersionUID = -705731434361674239L;

    private Long id;
    @JsonProperty("fandom_name")
    private String fandomName;
    @JsonProperty("fandom_parent_id")
    private Long fandomParentId;

    @JsonProperty("fandom_creator_id")
    private String fandomCreatorSn;

    @JsonProperty("fandom_avatar_url")
    private String fandomAvatarUrl;

    @JsonProperty("fandom_intro")
    private String fandomIntro;

    @JsonProperty("follower_count")
    private String followerCount;

    @JsonProperty("post_count")
    private String postCount;

    private String followed;
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

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public UserInfoResult getCreator() {
        return creator;
    }

    public void setCreator(UserInfoResult creator) {
        this.creator = creator;
    }
}
