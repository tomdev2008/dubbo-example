package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by LiZhe on 12/5/2015.
 * 创建fandom传入参数
 */
public class AddFandomParam extends AbstractToken {

    @NotBlank
    @JSONField(name="fandom_creator_sn")
    private String currentSn;

    @NotBlank
    @JSONField(name="fandom_name")
    private String fandomName;

    @JSONField(name="fandom_parent_id")
    private Long fandomParentId;

    @JSONField(name="fandom_avatar_url")
    private String fandomAvatarUrl;

    @JSONField(name="fandom_intro")
    private String fandomIntro;

    @JSONField(name="fandom_tag_list")
    private List<FandomTagParam> fandomTagParam;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
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

    public List<FandomTagParam> getFandomTagParam() {
        return fandomTagParam;
    }

    public void setFandomTagParam(List<FandomTagParam> fandomTagParam) {
        this.fandomTagParam = fandomTagParam;
    }
}
