package com.fansz.members.model.event;

import com.fansz.members.model.specialfocus.SpecialFocusParam;

import java.io.Serializable;

/**
 * Created by allan on 15/12/21.
 */
public class SpecialFocusEvent implements Serializable{
    private static final long serialVersionUID = -3131587799420844086L;

    private SpecialFocusParam param;

    public SpecialFocusEvent() {

    }

    public SpecialFocusEvent(SpecialFocusParam param) {
        this.param = param;
    }

    public SpecialFocusParam getParam() {
        return param;
    }

    public void setParam(SpecialFocusParam param) {
        this.param = param;
    }
}
