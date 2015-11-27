package com.fansz.members.model.param;

/**
 * Created by root on 15-11-3.
 */
public class ModifyProfilePara {

    private String nickName;

    private String birthday;

    private String gender;

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
}
