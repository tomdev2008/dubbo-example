package com.fansz.fandom.model.profile;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * Created by allan on 15/12/7.
 */
public class SetMemberParam extends AbstractToken {

    private String currentSn;

    @JSONField(name="member_sn")
    private String memberSn;

    @JSONField(name="member_type")
    private String memberType;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

}
