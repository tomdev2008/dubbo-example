package com.fansz.members.api.utils;

/**
 * Created by allan on 15/11/27.
 */
public enum VerifyCodeType {
    REGISTER("register"), RESET("reset");

    private String name;

    private VerifyCodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
