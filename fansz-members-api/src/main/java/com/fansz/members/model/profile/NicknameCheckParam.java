package com.fansz.members.model.profile;

import com.fansz.members.model.AbstractToken;

import javax.validation.constraints.Size;


public class NicknameCheckParam extends AbstractToken{

    @Size(min=1)

    private String sn;

    @Size(min=1)
    private String nickname;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
