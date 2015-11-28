package com.fansz.members.api.model;

import java.util.Date;

/**
 * Created by root on 15-11-27.
 */
public class FandomPostWholeEntity {

    private Integer id;

    private Integer memberId;

    private Integer fandomId;

    private String postContent;

    private Date postTime;

    private Integer likeCount;


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
        this.postContent = postContent;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
