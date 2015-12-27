package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.newsfeeds.model.profile.UserInfoResult;

import java.util.Date;

/**
 * Created by dell on 2015/12/7.
 */
public class GetPostInfoResult {
    @JSONField(name="post_id")
    private Long postId;
    @JSONField(name="post_content")
    private String postContent;
    @JSONField(name="post_newsfeeds")
    private String postNewsfeeds;
    @JSONField(name="post_title")
    private String postTitle;
    @JSONField(name="like_count")
    private int likes;
    @JSONField(name="liked")
    private int liked;
    @JSONField(name="comments_count")
    private int comments;
    @JSONField(name="post_time")
    private Date postTime;
    @JSONField(name="fandom_info")
    private GetPostFandomInfo fandomInfo;
    @JSONField(name="post_member")
    private UserInfoResult postMember;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public GetPostFandomInfo getFandomInfo() {
        return fandomInfo;
    }

    public void setFandomInfo(GetPostFandomInfo fandomInfo) {
        this.fandomInfo = fandomInfo;
    }

    public UserInfoResult getPostMember() {
        return postMember;
    }

    public void setPostMember(UserInfoResult postMember) {
        this.postMember = postMember;
    }
}
