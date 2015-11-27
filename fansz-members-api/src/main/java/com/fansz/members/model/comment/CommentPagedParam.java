package com.fansz.members.model.comment;


/**
 * Created by root on 15-11-4.
 */
public class CommentPagedParam {

    private Long postId;

    private String accessToken;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
