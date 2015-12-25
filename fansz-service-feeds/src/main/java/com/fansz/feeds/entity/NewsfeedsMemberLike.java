package com.fansz.feeds.entity;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "newsfeeds_member_like")
public class NewsfeedsMemberLike implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_sn")
    private String memberSn;

    @Column(name = "post_id")
    private Long postId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "like_time")
    private Date likeTime;

    public NewsfeedsMemberLike() {
    }

    public NewsfeedsMemberLike(String memberSn, Long postId, Date likeTime) {
        this.memberSn = memberSn;
        this.postId = postId;
        this.likeTime = likeTime;
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


    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getLikeTime() {
        return this.likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }


}


