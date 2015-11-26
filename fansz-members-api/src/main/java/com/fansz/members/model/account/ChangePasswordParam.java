package com.fansz.members.model.account;


/**
 * Created by root on 15-11-3.
 */
public class ChangePasswordParam {

    private Long uid;

    private String oldPassword;

    private String newPassword;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
