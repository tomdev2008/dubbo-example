package com.fansz.members.model.comment;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchParam extends PageParam implements Serializable {

    private static final long serialVersionUID = -3581758642463346451L;
    @JsonProperty("sn")
    private String memberSn;
    @JsonProperty("access_token")
    private String accessToken;
    private String searchVal;

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

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }
}
