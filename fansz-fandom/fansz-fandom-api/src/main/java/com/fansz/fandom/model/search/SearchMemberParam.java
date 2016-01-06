package com.fansz.fandom.model.search;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchMemberParam extends PageParam implements AccessTokenAware {

    private String currentSn;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "search_val")
    private String searchVal;

    @JSONField(name = "member_type")
    private String memberType;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
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
