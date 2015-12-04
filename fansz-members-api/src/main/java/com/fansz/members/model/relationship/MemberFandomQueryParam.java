package com.fansz.members.model.relationship;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 获取用户关注的fandom请求参数模型
 */
public class MemberFandomQueryParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 394668565041221041L;

    @JsonProperty("access_token")
    private String accessToken;

    private String sn;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
