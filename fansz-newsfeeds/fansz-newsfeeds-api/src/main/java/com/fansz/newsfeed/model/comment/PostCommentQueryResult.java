package com.fansz.newsfeed.model.comment;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by xuyubiao on 15/12/1.
 */
public class PostCommentQueryResult {

    @JSONField(name="id")
    private Long id;

    @JSONField(name="post_id")
    private Long postId;

    @JSONField(name="comment_parent_id")
    private Long commentParentId;

    @JSONField(name="comment_content")
    private String commentContent;

    @JSONField(name="commentator_sn")
    private String commentatorSn;

    @JSONField(name="commentator_nickname")
    private String commentatorNickname;

    @JSONField(name="comment_time")
    private Date commentTime;

    @JSONField(name="commentator_avatar")
    private String commentatorAvatar;

    @JSONField(name="orgin_sn")
    private String orginSn;

    @JSONField(name="orgin_content")
    private String orginContent;

    @JSONField(name="orgin_nickname")
    private String orginNickname;

    @JSONField(name="orgin_avatar")
    private String orginAvatar;

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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentatorSn() {
        return commentatorSn;
    }

    public void setCommentatorSn(String commentatorSn) {
        this.commentatorSn = commentatorSn;
    }

    public String getCommentatorNickname() {
        return commentatorNickname;
    }

    public void setCommentatorNickname(String commentatorNickname) {
        this.commentatorNickname = commentatorNickname;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentatorAvatar() {
        return commentatorAvatar;
    }

    public void setCommentatorAvatar(String commentatorAvatar) {
        this.commentatorAvatar = commentatorAvatar;
    }

    public String getOrginSn() {
        return orginSn;
    }

    public void setOrginSn(String orginSn) {
        this.orginSn = orginSn;
    }

    public String getOrginContent() {
        return orginContent;
    }

    public void setOrginContent(String orginContent) {
        this.orginContent = orginContent;
    }

    public String getOrginNickname() {
        return orginNickname;
    }

    public void setOrginNickname(String orginNickname) {
        this.orginNickname = orginNickname;
    }

    public String getOrginAvatar() {
        return orginAvatar;
    }

    public void setOrginAvatar(String orginAvatar) {
        this.orginAvatar = orginAvatar;
    }
}
