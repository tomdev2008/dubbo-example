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
}