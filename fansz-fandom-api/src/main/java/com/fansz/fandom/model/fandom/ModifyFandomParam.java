package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dell on 2015/12/16.
 */
public class ModifyFandomParam extends AbstractToken implements Serializable {
    private static final long serialVersionUID = 63976582296875325L;

    @NotBlank
    @JSONField(name="fandom_admin_sn")
    private String adminSn;

    @NotNull
    @JSONField(name="fandom_id")
    private Long id;

    @JSONField(name="fandom_name")
    private String fandomName;

    @JSONField(name="fandom_parent_id")
    private Long fandomParentId;

    @JSONField(name="fandom_creator_sn")
    private String currentSn;

    @JSONField(name="fandom_avatar_url")
    private String fandomAvatarUrl;

    @JSONField(name="fandom_intro")
    private String fandomIntro;

    public String getAdminSn() {
        return adminSn;
    }

    public void setAdminSn(String adminSn) {
        this.adminSn = adminSn;
    }


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

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
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
}
