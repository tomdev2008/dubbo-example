package com.fansz.members.model.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Size;

/**
 * Created by allan on 15/12/7.
 */
public class SetMemberParam {

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("operator_sn")
    private String operatorSn;

    @JsonProperty("member_type")
    private String memberType;

    @Size(min = 1)
    @JsonProperty("access_token")
    private String accessToken;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
