package com.fansz.members.api.entity;

import java.util.Date;

public class FandomMemberEntity {

    private Long id;

    private String memberSn;

    private String fandomSn;

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

    public String getFandomSn() {
        return fandomSn;
    }

    public void setFandomSn(String fandomSn) {
        this.fandomSn = fandomSn;
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