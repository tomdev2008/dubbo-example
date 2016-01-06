package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

/**
 * Created by allan on 15/11/26.
 */
public class FandomQueryParam extends PageParam implements AccessTokenAware {

    private static final long serialVersionUID = 6163860431759993812L;

    @JSONField(name="access_token")
    private String accessToken;

    private String currentSn;

    @JSONField(name="fandom_id")
    private String fandomId;

    @JSONField(name="fandom_parent_id")
    private String fandomParentId;

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

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

    public String getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(String fandomParentId) {
        this.fandomParentId = fandomParentId;
    }
}
