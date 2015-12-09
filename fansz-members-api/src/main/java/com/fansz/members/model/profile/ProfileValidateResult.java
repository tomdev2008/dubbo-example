package com.fansz.members.model.profile;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/12/9.
 */
public class ProfileValidateResult implements Serializable {

    private static final long serialVersionUID = 1505130676725194956L;

    private Boolean unique;

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }
}
