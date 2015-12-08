package com.fansz.members.model.post;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class GetMemberFandomPostsParam extends PageParam{

    @JsonProperty("fandom_id")
    private long fandomId;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;


    public long getFandomId() {
        return fandomId;
    }

    public void setFandomId(long fandomId) {
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
