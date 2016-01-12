package com.fansz.push.sender.model;

import java.io.Serializable;

public class ApnsCertConfig implements Serializable {
    private static final long serialVersionUID = -5136479671872688947L;

    private String certFile;

    private String password;

    public String getCertFile() {
        return certFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
