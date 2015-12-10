package com.fansz.members.api.event;

import com.fansz.members.model.specialfocus.SpecialFocusParam;
import org.springframework.context.ApplicationEvent;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialRealtionEvent extends ApplicationEvent {

    private String eventType;

    private SpecialFocusParam source;

    public SpecialRealtionEvent(SpecialFocusParam source) {
        super(source);
    }

    public SpecialRealtionEvent(String eventType,SpecialFocusParam source) {
        super(source);
        this.eventType=eventType;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public SpecialFocusParam getSource() {
        return source;
    }


}
