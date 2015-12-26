package com.fansz.fandom.model.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * 用户退出登陆传入参数
 */
public class LogoutParam extends AbstractToken {

    @JSONField(name="sn")
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
