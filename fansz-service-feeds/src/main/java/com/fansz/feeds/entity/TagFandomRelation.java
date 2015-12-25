package com.fansz.feeds.entity;
// Generated 2015-12-25 15:33:25 by Hibernate Tools 3.2.2.GA


import javax.persistence.*;
import java.util.Date;

/**
 * TagFandomRelation generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name="tag_fandom_relation"
    ,catalog="fansz"
)
public class TagFandomRelation  implements java.io.Serializable {


     private int id;
     private Integer fandomId;
     private Integer tagId;
     private Date createTime;

    public TagFandomRelation() {
    }

	
    public TagFandomRelation(int id) {
        this.id = id;
    }
    public TagFandomRelation(int id, Integer fandomId, Integer tagId, Date createTime) {
       this.id = id;
       this.fandomId = fandomId;
       this.tagId = tagId;
       this.createTime = createTime;
    }
   
     @Id 
    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="fandom_id")
    public Integer getFandomId() {
        return this.fandomId;
    }
    
    public void setFandomId(Integer fandomId) {
        this.fandomId = fandomId;
    }
    
    @Column(name="tag_id")
    public Integer getTagId() {
        return this.tagId;
    }
    
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", length=19)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }




}


