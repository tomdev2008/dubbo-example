package com.fansz.members.api.entity;

import java.util.Date;

public class FandomFollowEntity {
    private Integer id;

    private Integer memberId;

    private Integer fandomId;

    private Date joinTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getFandomId() {
        return fandomId;
    }

    public void setFandomId(Integer fandomId) {
        this.fandomId = fandomId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
}