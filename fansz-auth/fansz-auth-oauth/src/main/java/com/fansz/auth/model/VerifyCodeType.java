package com.fansz.auth.model;

/**
 * Created by allan on 15/11/27.
 */
public enum VerifyCodeType {
    REGISTER("register"), RESET("reset");

    private String name;

    VerifyCodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
