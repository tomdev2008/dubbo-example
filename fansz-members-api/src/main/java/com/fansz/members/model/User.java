package com.fansz.members.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by allan on 15/11/20.
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 8784044744081471744L;

    private Long userId;
    private String userAccount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
