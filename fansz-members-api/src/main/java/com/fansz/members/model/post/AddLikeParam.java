package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class AddLikeParam {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("post_id")
    private long postId;

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

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
