package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.newsfeeds.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * POST信息模型
 */
public class PostInfoResult implements Serializable {

    private static final long serialVersionUID = 7239881991070394937L;

    @JSONField(name = "post_id")
    private Long id;


    @JSONField(name = "post_title")
    private String postTitle;

    @JSONField(name = "post_content")
    private String postContent;

    @JSONField(name = "post_time")
    private Date postTime;

    @JSONField(name = "source_from")
    private String sourceFrom;

    @JSONField(name = "source_post_id")
    private Long sourcePostId;

    private String liked;

    @JSONField(name = "post_member")
    private Map<String, Object> userInfoResult;

    @JSONField(name = "liked_list")
    private List<Map<String, Object>> likedList;

    @JSONField(name = "comment_list")
    private List<PostCommentQueryResult> commentList;

    @JSONField(name = "fandom_id")
    private String fandomId;

    @JSONField(name = "fandom_avatar")
    private String fandomAvatarUrl;

    @JSONField(name = "fandom_name")
    private String fandomName;

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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public List<PostCommentQueryResult> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<PostCommentQueryResult> commentList) {
        this.commentList = commentList;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Long getSourcePostId() {
        return sourcePostId;
    }

    public void setSourcePostId(Long sourcePostId) {
        this.sourcePostId = sourcePostId;
    }

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public Map<String, Object> getUserInfoResult() {
        return userInfoResult;
    }

    public void setUserInfoResult(Map<String, Object> userInfoResult) {
        this.userInfoResult = userInfoResult;
    }

    public List<Map<String, Object>> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<Map<String, Object>> likedList) {
        this.likedList = likedList;
    }
}
