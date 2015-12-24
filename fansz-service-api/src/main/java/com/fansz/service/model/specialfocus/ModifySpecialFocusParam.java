package com.fansz.service.model.specialfocus;

import com.fansz.common.provider.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by dell on 2015/12/9.
 */
public class ModifySpecialFocusParam extends AbstractToken {

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("list")
    private List<SpecialFocusParam> list;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public List<SpecialFocusParam> getList() {
        return list;
    }

    public void setList(List<SpecialFocusParam> list) {
        this.list = list;
    }
}
