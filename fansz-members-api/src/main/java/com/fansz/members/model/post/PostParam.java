package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 用户点赞,传入参数模型
 */
public class PostParam implements Serializable {

    private static final long serialVersionUID = -1772542305173463716L;
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
