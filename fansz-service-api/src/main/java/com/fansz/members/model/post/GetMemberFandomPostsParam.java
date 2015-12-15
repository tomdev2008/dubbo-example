package com.fansz.members.model.post;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class GetMemberFandomPostsParam extends PageParam {

    @NotNull
    @JsonProperty("fandom_id")
    private Long fandomId;

    @NotBlank
    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;


    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
