package com.fansz.service.model.comment;


import com.fansz.common.provider.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 发表评论时传入参数
 */
public class AddCommentParam extends AbstractToken {

    @NotBlank
    @JsonProperty("commentator_sn")
    private String currentSn;

    @NotBlank
    @JsonProperty("comment_source")
    private String commentSource;

    @NotBlank
    @JsonProperty("comment_content")
    private String commentContent;

    @NotNull
    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("comment_parent_id")
    private Long commentParentId;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
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

}
