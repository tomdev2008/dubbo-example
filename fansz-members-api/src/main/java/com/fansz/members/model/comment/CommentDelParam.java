package com.fansz.members.model.comment;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class CommentDelParam implements Serializable{

    private static final long serialVersionUID = 7097767369484809045L;

    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("commentator_sn")
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
