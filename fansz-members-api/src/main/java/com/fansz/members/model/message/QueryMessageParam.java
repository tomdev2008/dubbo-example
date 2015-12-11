package com.fansz.members.model.message;

import com.fansz.members.model.AccessTokenAware;
import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/11.
 */
public class QueryMessageParam extends PageParam implements AccessTokenAware,Serializable {

    private static final long serialVersionUID = -4323802033134201079L;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
