package com.fansz.members.api.event;

/**
 * Created by allan on 15/12/10.
 */
public enum FandomEventType {

    ADD_SPECIAL("01"), REMOVE_SPECIAL("02");

    private String code;

    FandomEventType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
