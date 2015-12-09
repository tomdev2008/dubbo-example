package com.fansz.members.model.profile;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Size;

/**
 * Created by allan on 15/12/7.
 */
public class SetMemberParam extends AbstractToken {

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("operator_sn")
    private String operatorSn;

    @JsonProperty("member_type")
    private String memberType;


    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
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
