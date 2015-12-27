package com.fansz.newsfeeds.model.comment;


import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 发表评论时传入参数
 */
public class AddCommentParam extends AbstractToken {

    @NotBlank
    @JSONField(name="commentator_sn")
    private String currentSn;

    @NotBlank
    @JSONField(name="comment_source")
    private String commentSource;

    @NotBlank
    @JSONField(name="comment_content")
    private String commentContent;

    @NotNull
    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="comment_parent_id")
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
