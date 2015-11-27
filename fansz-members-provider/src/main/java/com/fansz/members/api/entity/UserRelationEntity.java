package com.fansz.members.api.entity;

public class UserRelationEntity {
    private Integer id;

    private Integer myMemberId;

    private Integer freindMemberId;

    private String relationStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMyMemberId() {
        return myMemberId;
    }

    public void setMyMemberId(Integer myMemberId) {
        this.myMemberId = myMemberId;
    }

    public Integer getFreindMemberId() {
        return freindMemberId;
    }

    public void setFreindMemberId(Integer freindMemberId) {
        this.freindMemberId = freindMemberId;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus == null ? null : relationStatus.trim();
    }
}