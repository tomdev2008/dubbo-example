package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.newsfeeds.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;

/**
 * POST信息模型
 */
public class PostInfoResult implements Serializable {

    private static final long serialVersionUID = 7239881991070394937L;

    private Long id;

    @JSONField(name = "post_title")
    private String postTitle;

    @JSONField(name = "post_content")
    private String postContent;

    @JSONField(name = "post_time")
    private Date postTime;

    private String liked;

    @JSONField(name = "post_member")
    private UserInfoResult userInfoResult;

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
