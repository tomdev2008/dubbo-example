package com.fansz.members.model.specialfocus;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
