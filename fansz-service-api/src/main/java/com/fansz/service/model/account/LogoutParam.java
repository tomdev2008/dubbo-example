package com.fansz.service.model.account;

import com.fansz.common.provider.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 用户退出登陆传入参数
 */
public class LogoutParam extends AbstractToken {

    @JsonProperty("sn")
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
