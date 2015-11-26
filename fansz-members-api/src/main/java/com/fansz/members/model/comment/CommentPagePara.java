package com.fansz.members.model.comment;


import com.fansz.members.model.param.PagePara;

/**
 * Created by root on 15-11-4.
 */
public class CommentPagePara extends PagePara{

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
