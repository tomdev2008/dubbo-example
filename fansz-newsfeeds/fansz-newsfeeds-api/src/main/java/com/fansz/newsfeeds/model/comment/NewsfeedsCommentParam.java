package com.fansz.newsfeeds.model.comment;


import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import com.fansz.newsfeeds.api.NewsfeedsCommentApi;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 发表评论时传入参数
 */
public class NewsfeedsCommentParam extends AbstractToken {

    @JSONField(name="comment_id")
    private Long id;

    @NotNull(groups = NewsfeedsCommentApi.addPostComment.class)
    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="comment_parent_id")
    private Long commentParentId;

    @NotBlank(groups = NewsfeedsCommentApi.addPostComment.class)
    @JSONField(name="commentator_sn")
    private String commentatorSn;

    @NotBlank(groups = NewsfeedsCommentApi.addPostComment.class)
    @JSONField(name="comment_content")
    private String commentContent;

    private Date commentTime;

    private String commentSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCommentatorSn() {
        return commentatorSn;
    }

    public void setCommentatorSn(String commentatorSn) {
        this.commentatorSn = commentatorSn;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(String commentSource) {
        this.commentSource = commentSource;
    }

    @Override
    public String getCurrentSn() {
        return null;
    }

    @Override
    public void setCurrentSn(String s) {

    }
}
