package com.fansz.members.api.event;

import com.fansz.members.model.specialfocus.SpecialFocusParam;
import org.springframework.context.ApplicationEvent;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialRealtionEvent extends ApplicationEvent {

    private FandomEventType eventType;

    private SpecialFocusParam param;


    public SpecialRealtionEvent(Object source, SpecialFocusParam param) {
        super(source);
        this.param = param;
    }

    public SpecialRealtionEvent(Object source, FandomEventType eventType, SpecialFocusParam param) {
        super(source);
        this.param = param;
        this.eventType = eventType;
    }

    public FandomEventType getEventType() {
        return eventType;
    }

    public SpecialFocusParam getParam() {
        return param;
    }
}
