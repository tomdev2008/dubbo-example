package com.fansz.members.api.entity;

public class UserRelationEntity {
    private Long id;

    private Long myMemberId;

    private Long freindMemberId;

    private String relationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMyMemberId() {
        return myMemberId;
    }

    public void setMyMemberId(Long myMemberId) {
        this.myMemberId = myMemberId;
    }

    public Long getFreindMemberId() {
        return freindMemberId;
    }

    public void setFreindMemberId(Long freindMemberId) {
        this.freindMemberId = freindMemberId;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus == null ? null : relationStatus.trim();
    }
}