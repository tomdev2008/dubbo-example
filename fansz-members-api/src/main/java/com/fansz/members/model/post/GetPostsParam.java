package com.fansz.members.model.post;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 分页查询用户所有fandom的post信息
 */
public class GetPostsParam extends PageParam {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String memberSn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }
}
