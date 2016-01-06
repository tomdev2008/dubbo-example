package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * Created by dell on 2016/1/4.
 */
public class JoinFandomsParam extends AbstractToken {

    private String currentSn;

    @NotNull
    @Size(min = 1)
    @JSONField(name = "fandom_ids")
    private List<String> fandomIds;

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
}
