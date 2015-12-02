package com.fansz.members.model.comment;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by xuyubiao on 15/12/1.
 */
public class CommentQueryFromFandom {

    private String sn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("comment_source")
    private String commentSource;
    @JsonProperty("post_id")
    private int postId;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(String commentSource) {
        this.commentSource = commentSource;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
