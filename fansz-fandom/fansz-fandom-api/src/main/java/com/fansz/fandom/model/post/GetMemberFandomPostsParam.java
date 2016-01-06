package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class GetMemberFandomPostsParam extends PageParam implements AccessTokenAware {

    @NotNull
    @JSONField(name="fandom_id")
    private Long fandomId;

    @NotBlank
    @JSONField(name="member_sn")
    private String memberSn;

    private String currentSn;

    @JSONField(name="access_token")
    private String accessToken;


    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }
}
