package com.fansz.members.tools;

/**
 * Created by allan on 15/11/30.
 */
public enum RelationShip {

    TO_BE_FRIEND("00"), FRIEND("10"), SPECIAL_FRIEND("11");

    private String code;

    private RelationShip(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
