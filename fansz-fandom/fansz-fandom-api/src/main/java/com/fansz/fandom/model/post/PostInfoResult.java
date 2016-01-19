package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;

/**
 * POST信息模型
 */
public class PostInfoResult implements Serializable {

    private static final long serialVersionUID = 7239881991070394937L;

    @JSONField(name="post_id")
    private Long id;

    @JSONField(name = "post_type")
    private String postType;

    @JSONField(name="post_title")
    private String postTitle;

    @JSONField(name="post_content")
    private String postContent;

    @JSONField(name="post_newsfeeds")
    private String postNewsfeeds;

    @JSONField(name="post_time")
    private Date postTime;

    private String liked;

    @JSONField(name="like_count")
    private Long likes;

    @JSONField(name="comments_count")
    private Long comments;

    @JSONField(name="fandom_info")
    private FandomInfoResult fandomInfoResult;

    @JSONField(name="post_member")
    private UserInfoResult userInfoResult;

    @JSONField(name = "effective_time")
    private Date effectiveTime;

    @JSONField(name = "voted")
    private String voted;

    @JSONField(name = "voted_option")
    private String votedOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
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

    public FandomInfoResult getFandomInfoResult() {
        return fandomInfoResult;
    }

    public void setFandomInfoResult(FandomInfoResult fandomInfoResult) {
        this.fandomInfoResult = fandomInfoResult;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getVoted() {
        return voted;
    }

    public void setVoted(String voted) {
        this.voted = voted;
    }

    public String getVotedOption() {
        return votedOption;
    }

    public void setVotedOption(String votedOption) {
        this.votedOption = votedOption;
    }
}
