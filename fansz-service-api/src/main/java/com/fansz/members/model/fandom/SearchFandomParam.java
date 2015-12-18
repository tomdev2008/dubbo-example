package com.fansz.members.model.fandom;

import com.fansz.members.model.AccessTokenAware;
import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/5.
 */
public class SearchFandomParam extends PageParam implements AccessTokenAware {

    private static final long serialVersionUID = 5059191023562439861L;
    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("search_val")
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
