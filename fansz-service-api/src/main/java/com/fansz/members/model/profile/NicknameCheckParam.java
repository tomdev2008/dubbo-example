package com.fansz.members.model.profile;

import com.fansz.members.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;


public class NicknameCheckParam extends AbstractToken{

    @NotBlank
    private String sn;

    @NotBlank
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
