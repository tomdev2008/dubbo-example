package com.fansz.db.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "feeds_fandom")
public class Fandom {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "fandom_name")
    private String fandomName;

    @Column(name = "fandom_parent_id")
    private Long fandomParentId;

    @Column(name = "fandom_admin_sn")
    private String fandomAdminSn;

    @Column(name = "fandom_creator_sn")
    private String fandomCreatorSn;

    @Column(name = "fandom_avatar_url")
    private String fandomAvatarUrl;

    @Column(name = "fandom_intro")
    private String fandomIntro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fandom_create_time")
    private Date fandomCreateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName == null ? null : fandomName.trim();
    }

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public String getFandomAdminSn() {
        return fandomAdminSn;
    }

    public void setFandomAdminSn(String fandomAdminSn) {
        this.fandomAdminSn = fandomAdminSn;
    }

    public String getFandomCreatorSn() {
        return fandomCreatorSn;
    }

    public void setFandomCreatorSn(String fandomCreatorSn) {
        this.fandomCreatorSn = fandomCreatorSn;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl == null ? null : fandomAvatarUrl.trim();
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro == null ? null : fandomIntro.trim();
    }

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }
}