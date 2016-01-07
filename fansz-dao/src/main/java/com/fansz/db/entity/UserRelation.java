package com.fansz.db.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "connects_member_relation")
public class UserRelation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "my_member_sn")
    private String myMemberSn;

    @Column(name = "friend_member_sn")
    private String friendMemberSn;

    @Column(name = "relation_status")
    private String relationStatus;

    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyMemberSn() {
        return myMemberSn;
    }

    public void setMyMemberSn(String myMemberSn) {
        this.myMemberSn = myMemberSn == null ? null : myMemberSn.trim();
    }

    public String getFriendMemberSn() {
        return friendMemberSn;
    }

    public void setFriendMemberSn(String friendMemberSn) {
        this.friendMemberSn = friendMemberSn;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus == null ? null : relationStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}