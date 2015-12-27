package com.fansz.fandom.model.specialfocus;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFocusParam extends AbstractToken {

    @JSONField(name="special_id")
    private Long id;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="special_member_sn")
    private String specialMemberSn;

    @JSONField(name="special_fandom_id")
    private Long specialFandomId;

    @JSONField(name="posting_tag")
    private Long postingTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSpecialMemberSn() {
        return specialMemberSn;
    }

    public void setSpecialMemberSn(String specialMemberSn) {
        this.specialMemberSn = specialMemberSn;
    }

    public Long getSpecialFandomId() {
        return specialFandomId;
    }

    public void setSpecialFandomId(Long specialFandomId) {
        this.specialFandomId = specialFandomId;
    }

    public Long getPostingTag() {
        return postingTag;
    }

    public void setPostingTag(Long postingTag) {
        this.postingTag = postingTag;
    }
}
