package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

/**
 * Created by dell on 2015/12/5.
 */
public class SearchFandomParam extends PageParam implements AccessTokenAware {

    private static final long serialVersionUID = 5059191023562439861L;
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
