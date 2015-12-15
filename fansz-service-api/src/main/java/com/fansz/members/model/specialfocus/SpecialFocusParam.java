package com.fansz.members.model.specialfocus;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFocusParam extends AbstractToken {

    @JsonProperty("special_id")
    private Long id;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("special_member_sn")
    private String specialMemberSn;

    @JsonProperty("special_fandom_id")
    private Long specialFandomId;

    @JsonProperty("posting_tag")
    private Long postingTag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSpecialMemberSn() {
        return specialMemberSn;
    }

    public void setSpecialMemberSn(String specialMemberSn) {
        this.specialMemberSn = specialMemberSn;
    }

    public Long getSpecialFandomId() {
        return specialFandomId;
    }

    public void setSpecialFandomId(Long specialFandomId) {
        this.specialFandomId = specialFandomId;
    }

    public Long getPostingTag() {
        return postingTag;
    }

    public void setPostingTag(Long postingTag) {
        this.postingTag = postingTag;
    }
}
