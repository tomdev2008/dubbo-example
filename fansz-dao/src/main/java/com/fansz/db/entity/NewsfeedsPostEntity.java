package com.fansz.db.entity;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "newsfeeds_member_post")
public class NewsfeedsPostEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_sn")
    private String memberSn;

    @Column(name = "post_content")
    private String postContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_time")
    private Date postTime;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "comment_count")
    private Long commentCount;

    public NewsfeedsPostEntity() {
    }

    public NewsfeedsPostEntity(String memberSn, String postContent, Date postTime, String postTitle, Long likeCount, Long commentCount) {
        this.memberSn = memberSn;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postTitle = postTitle;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMemberSn() {
        return this.memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }


    public String getPostContent() {
        return this.postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }


    public String getPostTitle() {
        return this.postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }


    public Long getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }


    public Long getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }


}


