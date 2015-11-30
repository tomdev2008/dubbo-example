package com.fansz.members.model.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详细信息
 */
public class UserInfoResult implements Serializable {

    private static final long serialVersionUID = 8448691870030409834L;

    private String sn;

    private String loginname;

    private String password;

    private String mobile;

    private String email;

    private String nickname;

    private String gender;

    private String birthday;

    @JsonProperty("member_avatar")
    private String memberAvatar;

    @JsonProperty("profile_createtime")
    private Date profileCreatetime;

    @JsonProperty("profile_updatetime")
    private Date profileUpdatetime;

    @JsonProperty("member_type")
    private String memberType;

    @JsonProperty("member_status")
    private String memberStatus;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getProfileUpdatetime() {
        return profileUpdatetime;
    }

    public void setProfileUpdatetime(Date profileUpdatetime) {
        this.profileUpdatetime = profileUpdatetime;
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
}
