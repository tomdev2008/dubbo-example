package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class RegisterResult implements Serializable {
    private static final long serialVersionUID = -6646623722931553825L;
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
