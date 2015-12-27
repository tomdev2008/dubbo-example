package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

/**
 * 分页查询用户所有fandom的post信息
 */
public class GetPostsParam extends PageParam implements AccessTokenAware {

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="member_sn")
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
