package com.fansz.fandom.model.profile;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息
 */
public class UserInfoResult implements Serializable {

    private static final long serialVersionUID = 8448691870030409834L;

    private String sn;

    private String email;

    private String mobile;

    private String nickname;

    private String gender;

    private String birthday;

    @JSONField(name="member_avatar")
    private String memberAvatar;

    @JSONField(name="profile_createtime")
    private Date profileCreatetime;

    @JSONField(name="member_type")
    private String memberType;

    private String signature;

    private String relationship;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public Date getProfileCreatetime() {
        return profileCreatetime;
    }

    public void setProfileCreatetime(Date profileCreatetime) {
        this.profileCreatetime = profileCreatetime;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}