package com.fansz.members.model.fandom;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class FandomQueryParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 6163860431759993812L;

    @JsonProperty("access_token")
    private String accessKey;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("fandom_id")
    private String fandomId;

    @JsonProperty("fandom_parent_id")
    private String fandomParentId;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(String fandomParentId) {
        this.fandomParentId = fandomParentId;
    }
}
