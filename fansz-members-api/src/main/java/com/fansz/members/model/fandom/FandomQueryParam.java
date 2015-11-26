package com.fansz.members.model.fandom;

/**
 * Created by allan on 15/11/26.
 */
public class FandomQueryParam {

    private String accessKey;

    private Long fandomId;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }
}
