package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/4.
 */
public class RemovePostParam implements Serializable{

    private static final long serialVersionUID = 6298573938660712413L;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("member_sn")
    private String memberSn;
    @JsonProperty("post_id")
    private Long postId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
