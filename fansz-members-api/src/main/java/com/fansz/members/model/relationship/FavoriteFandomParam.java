package com.fansz.members.model.relationship;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 查询用户关注的fandom,传入参数
 */
public class FavoriteFandomParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 394668565041221041L;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("sn")
    private String uid;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
