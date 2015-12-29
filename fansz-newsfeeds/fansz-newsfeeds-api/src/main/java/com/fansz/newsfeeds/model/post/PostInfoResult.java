package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.newsfeeds.model.fandom.FandomInfoResult;
import com.fansz.newsfeeds.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * POST信息模型
 */
public class PostInfoResult implements Serializable {

    private static final long serialVersionUID = 7239881991070394937L;

    @JSONField(name="post_id")
    private Long id;


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


    @JSONField(name="post_member")
    private UserInfoResult userInfoResult;

    @JSONField(name = "liked_list")
    private List<UserInfoResult> likedList;

    //TODO comment bean 需要改为heli新增的comment对象
    @JSONField(name = "comment_list")
    private List<PostCommentQueryResult> commentList;

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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public List<UserInfoResult> getLikedList() {
        return likedList;
    }

    public void setLikedList(List<UserInfoResult> likedList) {
        this.likedList = likedList;
    }

    public List<PostCommentQueryResult> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<PostCommentQueryResult> commentList) {
        this.commentList = commentList;
    }
}
