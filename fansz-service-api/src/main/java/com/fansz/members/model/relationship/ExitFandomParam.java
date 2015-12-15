package com.fansz.members.model.relationship;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * 取消关注fandom传入参数模型
 */
public class ExitFandomParam extends AbstractToken {

    @NotBlank
    @JsonProperty("member_sn")
    private String memberSn;

    @NotBlank
    @JsonProperty("fandom_id")
    private String fandomId;


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

}
