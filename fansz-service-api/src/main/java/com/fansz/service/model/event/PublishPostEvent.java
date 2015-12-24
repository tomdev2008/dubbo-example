package com.fansz.service.model.event;

import java.io.Serializable;

/**
 * 朋友圈发布事件
 */
public class PublishPostEvent implements Serializable {

    private Long postId;

    private String memberSn;

    public PublishPostEvent(Long postId, String memberSn) {
        this.postId = postId;
        this.memberSn = memberSn;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }
}
