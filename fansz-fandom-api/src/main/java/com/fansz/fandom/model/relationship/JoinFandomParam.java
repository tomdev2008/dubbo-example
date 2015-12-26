package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by allan on 15/12/1.
 */
public class JoinFandomParam implements Serializable {

    private static final long serialVersionUID = 4543574868180635074L;

    @NotBlank
    @JSONField(name="member_sn")
    private String memberSn;

    @NotBlank
    @JSONField(name="fandom_id")
    private String fandomId;

    @JSONField(name="access_token")
    private String accessToken;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
