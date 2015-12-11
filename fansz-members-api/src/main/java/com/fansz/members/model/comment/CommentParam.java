package com.fansz.members.model.comment;


import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by root on 15-11-4.
 */
public class CommentParam extends AbstractToken {

    @Size(min = 1)
    @JsonProperty("commentator_sn")
    private String commentatorSn;

    @Size(min = 1)
    @JsonProperty("comment_source")
    private String commentSource;

    @Size(min = 1)
    @JsonProperty("comment_content")
    private String commentContent;

    @Size(min = 1)
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("comment_parent_id")
    private Long commentParentId;

    @Size(min = 1)
    @JsonProperty("access_token")
    private String accessToken;

    public String getCommentatorSn() {
        return commentatorSn;
    }

    public void setCommentatorSn(String commentatorSn) {
        this.commentatorSn = commentatorSn;
    }


    public String getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(String commentSource) {
        this.commentSource = commentSource;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Long commentParentId) {
        this.commentParentId = commentParentId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
