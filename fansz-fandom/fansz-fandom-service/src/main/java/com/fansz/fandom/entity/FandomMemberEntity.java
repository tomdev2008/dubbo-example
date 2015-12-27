package com.fansz.fandom.entity;

import java.util.Date;

public class FandomMemberEntity {

    private Long id;

    private String memberSn;

    private String fandomId;

    private String infatuation;

    private Date joinTime;

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

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getInfatuation() {
        return infatuation;
    }

    public void setInfatuation(String infatuation) {
        this.infatuation = infatuation;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
}