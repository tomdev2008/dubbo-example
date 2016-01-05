package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * Created by dell on 2016/1/4.
 */
public class JoinFandomsParam implements Serializable {
    private static final long serialVersionUID = 942909119420766685L;

    @NotBlank
    @JSONField(name="member_sn")
    private String memberSn;

    @NotNull
    @Size(min=1)
    @JSONField(name="fandom_ids")
    private List<String> fandomIds;

    @JSONField(name="access_token")
    private String accessToken;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public List<String> getFandomIds() {
        return fandomIds;
    }

    public void setFandomIds(List<String> fandomIds) {
        this.fandomIds = fandomIds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
