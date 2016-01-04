package com.fansz.auth.model;

import com.fansz.common.provider.model.AbstractToken;
import com.fansz.common.provider.model.AccessTokenAware;

/**
 * Created by allan on 16/1/4.
 */
public class OssTokenParam extends AbstractToken {
    private String currentSn;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }
}
