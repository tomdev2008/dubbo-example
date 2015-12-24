package com.fansz.service.api.entity;

import java.util.Date;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class MemberPostEntity {

    private long id;

    private String postTitle;

    private String postContent;

    private String postNewsfeeds;

    private Date postTime;

    private UserEntity userEntiy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserEntity getUserEntiy() {
        return userEntiy;
    }

    public void setUserEntiy(UserEntity userEntiy) {
        this.userEntiy = userEntiy;
    }
}
