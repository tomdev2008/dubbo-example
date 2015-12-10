package com.fansz.members.model.specialfocus;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialMemberResult implements Serializable{

    private static final long serialVersionUID = 6698297114032121648L;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("member_sn")
    private String memberSn;

    private String nickname;

    private String mobile;

    private String email;

    private String gender;

    private String birthday;

    @JsonProperty("member_avatar")
    private String memberAvatar;

    @JsonProperty("profile_createtime")
    private Date profileCreatetime;

    @JsonProperty("member_type")
    private String memberType;

    @JsonProperty("member_status")
    private String memberStatus;

    private String signature;

    @JsonProperty("postion_tag")
    private Long postionTag;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getPostionTag() {
        return postionTag;
    }

    public void setPostionTag(Long postionTag) {
        if(this.memberSn != null) {
            this.postionTag = postionTag;
        }
    }
}
