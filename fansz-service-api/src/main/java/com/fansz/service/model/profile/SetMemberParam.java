package com.fansz.service.model.profile;

import com.fansz.common.provider.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by allan on 15/12/7.
 */
public class SetMemberParam extends AbstractToken {

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("operator_sn")
    private String operatorSn;

    @JsonProperty("member_type")
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
