package com.fansz.fandom.model.messagecenter;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

import java.io.Serializable;

/**
 * Created by allan on 15/12/11.
 */
public class QueryMessageParam extends PageParam implements AccessTokenAware,Serializable {

    private static final long serialVersionUID = -4323802033134201079L;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="access_token")
    private String accessToken;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
