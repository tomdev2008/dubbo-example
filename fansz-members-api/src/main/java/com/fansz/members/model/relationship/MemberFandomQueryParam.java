package com.fansz.members.model.relationship;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/2.
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
