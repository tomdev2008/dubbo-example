package com.fansz.members.model;

import java.util.Date;
import java.util.List;

public class Post {

    private String id;

    private String title;

    private String content;

    private List<User> likeUsers;

    private User user;

    // 来源
    private Fandom source;

    private Date createTime;

    private Date updateTime;
    //是否已赞
    private String liked = "0";
    // 评论数
    private int commentsCount;
    // 点赞人数
    private int likeCount;
    private List<Comment> comments;

    private String type;//normal(text & image)|movie|audio|vote

    private String direction;//private, fandom

    private List<String> fandoms;

    private List<Attachment> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<User> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<User> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Fandom getSource() {
        return source;
    }

    public void setSource(Fandom source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<String> getFandoms() {
        return fandoms;
    }

    public void setFandoms(List<String> fandoms) {
        this.fandoms = fandoms;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}