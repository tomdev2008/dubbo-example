package com.fansz.members.model.comment;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;


/**
 * Created by xuyubiao on 15/12/1.
 */
public class CommentQueryFromFandomResult {

    private long id;
    private int postId;
    private String commentContent;
    private String commentatorSn;
    private String commentatorNickname;
    private Date commentTime;
    private String commentatorAvatar;
    private String orginSn;
    private String orginContent;
    private Date orginNickname;
    private String orginAvatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public Date getOrginNickname() {
        return orginNickname;
    }

    public void setOrginNickname(Date orginNickname) {
        this.orginNickname = orginNickname;
    }

    public String getOrginAvatar() {
        return orginAvatar;
    }

    public void setOrginAvatar(String orginAvatar) {
        this.orginAvatar = orginAvatar;
    }
}
