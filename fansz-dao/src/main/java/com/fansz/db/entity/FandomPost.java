package com.fansz.db.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "feeds_member_post_fandom")
public class FandomPost {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_sn")
    @JSONField(name = "member_sn")
    private String memberSn;

    @Column(name = "fandom_id")
    @JSONField(name = "fandom_id")
    private Long fandomId;

    @Column(name = "post_title")
    @JSONField(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    @JSONField(name = "post_content")
    private String postContent;

    @Column(name = "post_newsfeeds")
    @JSONField(name = "post_newsfeeds")
    private String postNewsfeeds;

    @Column(name = "post_level")
    @JSONField(name = "post_level")
    private String postLevel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_time")
    @JSONField(name = "post_time")
    private Date postTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent == null ? null : postContent.trim();
    }

    public String getPostNewsfeeds() {
        return postNewsfeeds;
    }

    public void setPostNewsfeeds(String postNewsfeeds) {
        this.postNewsfeeds = postNewsfeeds;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }
}