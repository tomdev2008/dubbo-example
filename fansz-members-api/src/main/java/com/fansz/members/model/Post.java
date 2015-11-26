package com.fansz.members.model;

import com.fansz.appservice.resource.param.PostParam;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Data
@NoArgsConstructor
@Document(collection="post")
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