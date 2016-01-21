package com.fansz.token.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * Created by wukai on 16/1/20.
 */
public class IMTokenParam extends AbstractToken {

    private String currentSn;

    @JSONField(name = "app_key")
    private String appKey;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
