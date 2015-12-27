package com.fansz.fandom.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/4.
 */
public class SearchPostParam extends PageParam implements AccessTokenAware, Serializable {

    private static final long serialVersionUID = -2377621602540990816L;
    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="search_val")
    private String searchVal;

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

}
