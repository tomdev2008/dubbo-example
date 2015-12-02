package com.fansz.members.model.fandom;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/26.
 */
public class FandomQueryParam implements Serializable {

    private static final long serialVersionUID = 6163860431759993812L;

    private String accessKey;

    @JsonProperty("fandom_id")
    private Long fandomId;

    @JsonProperty("fandom_parent_id")
    private Long fandomParentId;

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

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }
}
