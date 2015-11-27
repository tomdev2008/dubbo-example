package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class RegisterResult implements Serializable {
    private static final long serialVersionUID = -6646623722931553825L;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
