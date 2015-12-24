package com.fansz.service.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 分页查询用户所有fandom的post信息
 */
public class GetPostsParam extends PageParam implements AccessTokenAware {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String currentSn;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }
}
