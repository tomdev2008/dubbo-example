package com.fansz.fandom.model.specialfocus;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

import java.util.List;

/**
 * Created by dell on 2015/12/9.
 */
public class ModifySpecialFocusParam extends AbstractToken {

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="list")
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
