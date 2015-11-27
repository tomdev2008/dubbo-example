package com.fansz.members.api.entity;

import java.util.Date;

public class FandomPostEntity {
    private Integer id;

    private Integer memberId;

    private Integer fandomId;

    private String postContent;

    private Date postTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getFandomId() {
        return fandomId;
    }

    public void setFandomId(Integer fandomId) {
        this.fandomId = fandomId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent == null ? null : postContent.trim();
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}