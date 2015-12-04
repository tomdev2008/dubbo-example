package com.fansz.members.model.profile;


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by root on 15-11-3.
 */
public class ModifyProfileParam implements Serializable {

    private static final long serialVersionUID = -2028355548744485358L;

    @JsonProperty("member_sn")
    private String sn;

    @JsonProperty("access_token")
    private String accessToken;

    private String nickname;

    private String birthday;

    private String mobile;

    private String email;

    private String gender;

    @JsonProperty("member_avatar")
    private String memberAvatar;

    @JsonProperty("member_type")
    private String memberType;

    @JsonProperty("sn")
    private String operatorSn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getOperatorSn() {
        return operatorSn;
    }

    public void setOperatorSn(String operatorSn) {
        this.operatorSn = operatorSn;
    }
}
