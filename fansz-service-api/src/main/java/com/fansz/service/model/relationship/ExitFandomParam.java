package com.fansz.service.model.relationship;

import com.fansz.common.provider.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 取消关注fandom传入参数模型
 */
public class ExitFandomParam extends AbstractToken {

    @NotBlank
    @JsonProperty("member_sn")
    private String currentSn;

    @NotBlank
    @JsonProperty("fandom_id")
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
