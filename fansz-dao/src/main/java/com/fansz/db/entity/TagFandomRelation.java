package com.fansz.db.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "tag_fandom_relation")
public class TagFandomRelation implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "fandom_id")
    private Long fandomId;

    @Column(name = "tag_id")
    private Long tagId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public TagFandomRelation() {
    }


    public TagFandomRelation(Long id) {
        this.id = id;
    }

    public TagFandomRelation(Long id, Long fandomId, Long tagId, Date createTime) {
        this.id = id;
        this.fandomId = fandomId;
        this.tagId = tagId;
        this.createTime = createTime;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getFandomId() {
        return this.fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }


    public Long getTagId() {
        return this.tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }


    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}


