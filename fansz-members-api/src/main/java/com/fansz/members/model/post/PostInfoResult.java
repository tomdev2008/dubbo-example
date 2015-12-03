package com.fansz.members.model.post;

import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * POST信息模型
 */
public class PostInfoResult implements Serializable {

    private static final long serialVersionUID = 7239881991070394937L;

    @JsonProperty("post_id")
    private Long id;
    @JsonProperty("fandom_Id")
    private Long fandomId;
    @JsonProperty("post_title")
    private String postTitle;
    @JsonProperty("post_content")
    private String postContent;
    @JsonProperty("post_newsfeeds")
    private String postNewsfeeds;
    @JsonProperty("post_time")
    private Date postTime;
    @JsonProperty("liked")
    private String followed;
    @JsonProperty("likeCount")
    private Long likes;
    @JsonProperty("commentsCount")
    private Long comments;
    @JsonProperty("post_member")
    private UserInfoResult userInfoResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostNewsfeeds() {
        return postNewsfeeds;
    }

    public void setPostNewsfeeds(String postNewsfeeds) {
        this.postNewsfeeds = postNewsfeeds;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public UserInfoResult getUserInfoResult() {
        return userInfoResult;
    }

    public void setUserInfoResult(UserInfoResult userInfoResult) {
        this.userInfoResult = userInfoResult;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }


}
