package com.fansz.db.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "feeds_member_push_post")
public class PushPost implements java.io.Serializable {

    private static final long serialVersionUID = 6504188403343423076L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_sn")
    private String memberSn;

    @Column(name = "post_id")
    private Long postId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private Date ceratetime;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getCeratetime() {
        return ceratetime;
    }

    public void setCeratetime(Date ceratetime) {
        this.ceratetime = ceratetime;
    }
}


