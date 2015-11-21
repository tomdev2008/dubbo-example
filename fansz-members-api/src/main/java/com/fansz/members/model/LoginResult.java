package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/20.
 */
public class LoginResult implements Serializable {
    private static final long serialVersionUID = -2727440646612614067L;

    private String status;

    private String message;

    private Object result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
