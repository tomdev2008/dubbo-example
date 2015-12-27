package com.fansz.auth.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * 修改密码传入参数
 */
public class ChangePasswordParam extends AbstractToken {

    @JSONField(name="sn")
    private String currentSn;

    @JSONField(name="old_password")
    private String oldPassword;

    @JSONField(name="new_password")
    private String newPassword;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
