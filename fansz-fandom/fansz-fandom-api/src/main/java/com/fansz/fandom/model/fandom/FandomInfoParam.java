package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

import javax.validation.constraints.NotNull;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class FandomInfoParam extends AbstractToken {

    private String currentSn;

    @NotNull
    @JSONField(name="fandom_id")
    private Long fandomId;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }
}
