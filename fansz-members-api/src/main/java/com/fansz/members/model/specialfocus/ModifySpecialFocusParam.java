package com.fansz.members.model.specialfocus;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/9.
 */
public class ModifySpecialFocusParam implements Serializable {

    private static final long serialVersionUID = -3843281253692357382L;
    @JsonProperty("member_sn")
    private String memberSn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("list")
    private List<SpecialFocusParam> list;

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

    public List<SpecialFocusParam> getList() {
        return list;
    }

    public void setList(List<SpecialFocusParam> list) {
        this.list = list;
    }
}
