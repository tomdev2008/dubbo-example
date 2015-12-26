package com.fansz.db.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "tag_ceter")
public class TagCeter implements java.io.Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "creator_sn")
    private String creatorSn;

    @Column(name = "tag_type")
    private Long tagType;

    public TagCeter() {
    }


    public TagCeter(Long id, String tagName, Date createTime, String creatorSn, Long tagType) {
        this.id = id;
        this.tagName = tagName;
        this.createTime = createTime;
        this.creatorSn = creatorSn;
        this.tagType = tagType;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getCreatorSn() {
        return this.creatorSn;
    }

    public void setCreatorSn(String creatorSn) {
        this.creatorSn = creatorSn;
    }


    public Long getTagType() {
        return this.tagType;
    }

    public void setTagType(Long tagType) {
        this.tagType = tagType;
    }


}


