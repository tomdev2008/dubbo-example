package com.fansz.service.model.event;

import com.fansz.service.model.specialfocus.SpecialFocusParam;

import java.io.Serializable;

/**
 * Created by allan on 15/12/21.
 */
public class UnSpecialFocusEvent implements Serializable {
    private SpecialFocusParam param;

    public UnSpecialFocusEvent(){}

    public UnSpecialFocusEvent(SpecialFocusParam param) {
        this.param = param;
    }

    public SpecialFocusParam getParam() {
        return param;
    }

    public void setParam(SpecialFocusParam param) {
        this.param = param;
    }
}
