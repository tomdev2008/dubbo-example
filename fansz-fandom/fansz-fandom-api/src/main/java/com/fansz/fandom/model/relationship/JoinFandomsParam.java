package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

import java.util.List;


/**
 * Created by dell on 2016/1/4.
 */
public class JoinFandomsParam extends AbstractToken {

    private String currentSn;


    @JSONField(name = "fandom_ids")
    private List<String> fandomIds;

    @JSONField(name = "fandom_id")
    private String fandomId;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public List<String> getFandomIds() {
        return fandomIds;
    }

    public void setFandomIds(List<String> fandomIds) {
        this.fandomIds = fandomIds;
    }

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }
}
