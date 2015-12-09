package com.fansz.members.model.account;


import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by root on 15-11-3.
 */
public class ChangePasswordParam extends AbstractToken {

    @JsonProperty("sn")
    private String uid;

    @JsonProperty("old_password")
    private String oldPassword;

    @JsonProperty("new_password")
    private String newPassword;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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
