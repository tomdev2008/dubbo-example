package com.fansz.members.model.comment;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class CommentDelParam implements Serializable{

    private static final long serialVersionUID = 7097767369484809045L;
    private Long commentId;

    private String accessToken;

    private Long postId;

    private String commentatorSn;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCommentatorSn() {
        return commentatorSn;
    }

    public void setCommentatorSn(String commentatorSn) {
        this.commentatorSn = commentatorSn;
    }
}
