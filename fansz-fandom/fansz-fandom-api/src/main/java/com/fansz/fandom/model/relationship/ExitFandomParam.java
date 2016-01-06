package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 取消关注fandom传入参数模型
 */
public class ExitFandomParam extends AbstractToken {

    private String currentSn;

    @NotBlank
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

    public String getFandomId() {
        return fandomId;
    }

    public void setFandomId(String fandomId) {
        this.fandomId = fandomId;
    }

}
