package com.fansz.members.model.fandom;

import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Date;

/**
 * Created by root on 15-11-4.
 */
public class FandomParam {

    private Long id;
    private String fandomName;
    private Long fandomParentId;
    private Long fandomAdminId;
    private Long fandomCreatorId;
    private String fandomAvatarUrl;
    private String fandomIntro;
    private Date fandomCreateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }

    public Long getFandomAdminId() {
        return fandomAdminId;
    }

    public void setFandomAdminId(Long fandomAdminId) {
        this.fandomAdminId = fandomAdminId;
    }

    public Long getFandomCreatorId() {
        return fandomCreatorId;
    }

    public void setFandomCreatorId(Long fandomCreatorId) {
        this.fandomCreatorId = fandomCreatorId;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro;
    }

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }

}
