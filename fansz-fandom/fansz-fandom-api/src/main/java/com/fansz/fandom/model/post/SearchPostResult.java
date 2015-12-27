package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.fandom.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/5.
 */
public class SearchPostResult implements Serializable{

    private static final long serialVersionUID = -7786572952854806697L;
    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="post_title")
    private String postTitle;

    @JSONField(name="post_content")
    private String postContent;

    @JSONField(name="post_newsfeeds")
    private String postNewsfeeds;

    @JSONField(name="post_time")
    private Date postTime;

    @JSONField(name="post_member")
    private UserInfoResult postMember;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public UserInfoResult getPostMember() {
        return postMember;
    }

    public void setPostMember(UserInfoResult postMember) {
        this.postMember = postMember;
    }
}
