package com.fansz.fandom.model.search;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.PageParam;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchMemberParam extends PageParam implements Serializable {

    private static final long serialVersionUID = -3581758642463346451L;
    @JSONField(name="sn")
    private String memberSn;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="search_val")
    private String searchVal;

    @JSONField(name="member_type")
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
}
