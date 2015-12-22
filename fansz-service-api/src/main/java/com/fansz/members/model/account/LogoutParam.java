package com.fansz.members.model.account;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by allan on 15/11/30.
 */
public class LogoutParam extends AbstractToken {

    @JsonProperty("sn")
    private String currentSn;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }
}
