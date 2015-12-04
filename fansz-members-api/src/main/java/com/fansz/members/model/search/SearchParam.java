package com.fansz.members.model.search;

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

    @JsonProperty("search_val")
    private String searchVal;

    @JsonProperty("member_type")
    private String memberType;

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
<<<<<<< HEAD
}
=======

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
>>>>>>> origin/master
