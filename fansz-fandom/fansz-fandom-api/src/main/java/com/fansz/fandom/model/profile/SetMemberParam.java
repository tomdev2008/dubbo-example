package com.fansz.fandom.model.profile;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * Created by allan on 15/12/7.
 */
public class SetMemberParam extends AbstractToken {

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="operator_sn")
    private String operatorSn;

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

    public String getOperatorSn() {
        return operatorSn;
    }

    public void setOperatorSn(String operatorSn) {
        this.operatorSn = operatorSn;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

}
