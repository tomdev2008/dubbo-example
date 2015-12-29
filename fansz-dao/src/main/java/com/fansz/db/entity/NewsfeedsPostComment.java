package com.fansz.db.entity;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "newsfeeds_member_comment")
public class NewsfeedsPostComment implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "comment_parent_id")
    private Long commentParentId;

    @Column(name = "commentator_sn")
    private String commentatorSn;

    @Column(name = "comment_content")
    private String commentContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_time")
    private Date commentTime;

    public NewsfeedsPostComment() {
    }

    public NewsfeedsPostComment(Long postId, Long commentParentId, String commentatorSn, String commentContent, Date commentTime, String commentSource) {
        this.postId = postId;
        this.commentParentId = commentParentId;
        this.commentatorSn = commentatorSn;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


    public Long getCommentParentId() {
        return this.commentParentId;
    }

    public void setCommentParentId(Long commentParentId) {
        this.commentParentId = commentParentId;
    }


    public String getCommentatorSn() {
        return this.commentatorSn;
    }

    public void setCommentatorSn(String commentatorSn) {
        this.commentatorSn = commentatorSn;
    }


    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

}


