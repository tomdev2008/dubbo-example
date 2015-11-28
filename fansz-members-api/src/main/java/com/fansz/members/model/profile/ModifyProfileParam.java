package com.fansz.members.model.profile;


import java.io.Serializable;

/**
 * Created by root on 15-11-3.
 */
public class ModifyProfileParam implements Serializable{

    private static final long serialVersionUID = -2028355548744485358L;

    private String uid;

    private String accessToken;

    private String nickName;

    private String birthday;

    private String gender;

    private String memberAvatar;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
