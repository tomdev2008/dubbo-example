package com.fansz.members.api.entity;

import java.util.Date;

public class FandomPostLikeEntity {
    private Long id;

    private Long memberSn;

    private Long postId;

    private Date likeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(Long memberSn) {
        this.memberSn = memberSn;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

}