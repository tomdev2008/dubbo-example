package com.fansz.members.model.fandom;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class FandomInfoParam extends AbstractToken {

    @JsonProperty("member_sn")
    private String memberSn;

    @NotNull
    @JsonProperty("fandom_id")
    private Long fandomId;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }


    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }
}
