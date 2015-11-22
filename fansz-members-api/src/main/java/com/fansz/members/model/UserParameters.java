package com.fansz.members.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by allan on 15/11/20.
 */
@XmlRootElement
public class UserParameters implements Serializable {

    private static final long serialVersionUID = 8784044744081471744L;

    private String userAccount;

    private String appKey;

    private String password;

    private String verifyCode;


    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
