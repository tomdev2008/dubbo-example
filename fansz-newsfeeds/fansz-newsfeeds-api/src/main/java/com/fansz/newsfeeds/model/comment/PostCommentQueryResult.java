package com.fansz.newsfeeds.model.comment;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by xuyubiao on 15/12/1.
 */
public class PostCommentQueryResult implements Serializable {

    private static final long serialVersionUID = -3314595763754474476L;

    private Long id;

    @JSONField(name = "post_id")
    private Long postId;

    @JSONField(name = "comment_parent_id")
    private Long commentParentId;

    @JSONField(name = "comment_content")
    private String commentContent;

    @JSONField(name = "commentator_sn")
    private String commentatorSn;

    @JSONField(name = "commentator_nickname")
    private String commentatorNickname;

    @JSONField(name = "comment_time")
    private Date commentTime;

    @JSONField(name = "commentator_avatar")
    private String commentatorAvatar;

    @JSONField(name = "origin_sn")
    private String originSn;

    @JSONField(name = "origin_content")
    private String originContent;

    @JSONField(name = "origin_nickname")
    private String originNickname;

    @JSONField(name = "origin_avatar")
    private String originAvatar;

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

    public String getOriginSn() {
        return originSn;
    }

    public void setOriginSn(String originSn) {
        this.originSn = originSn;
    }

    public String getOriginContent() {
        return originContent;
    }

    public void setOriginContent(String originContent) {
        this.originContent = originContent;
    }

    public String getOriginNickname() {
        return originNickname;
    }

    public void setOriginNickname(String originNickname) {
        this.originNickname = originNickname;
    }

    public String getOriginAvatar() {
        return originAvatar;
    }

    public void setOriginAvatar(String originAvatar) {
        this.originAvatar = originAvatar;
    }
}
