package com.fansz.members.model.post;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/4.
 */
public class SearchPostParam extends PageParam implements Serializable{

    private static final long serialVersionUID = -2377621602540990816L;
    @JsonProperty("member_sn")
    private String memberSn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("search_val")
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
