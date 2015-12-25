package com.fansz.feeds.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "log_member_op")
public class MemberOpLog implements java.io.Serializable {

    private static final long serialVersionUID = -1050418657698836985L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_sn")
    private String memberSn;

    @Column(name = "op_type")
    private String opType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public MemberOpLog() {
    }


    public MemberOpLog(Long id) {
        this.id = id;
    }

    public MemberOpLog(Long id, String memberSn, String opType, Date createTime) {
        this.id = id;
        this.memberSn = memberSn;
        this.opType = opType;
        this.createTime = createTime;
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


    public String getOpType() {
        return this.opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}


