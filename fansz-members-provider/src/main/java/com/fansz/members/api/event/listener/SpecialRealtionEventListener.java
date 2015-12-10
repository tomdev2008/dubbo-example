package com.fansz.members.api.event.listener;

import com.fansz.members.api.event.SpecialRealtionEvent;
import com.fansz.members.api.service.SpecialFocusService;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/9.
 */
@Component
public class SpecialRealtionEventListener implements ApplicationListener<SpecialRealtionEvent> {
    @Autowired
    private SpecialFocusService specialFocusService;
    @Override
    public void onApplicationEvent(SpecialRealtionEvent specialRealtionEvent) {
        String eventType = specialRealtionEvent.getEventType();
        SpecialFocusParam param =  specialRealtionEvent.getSource();
        if("add".equals(eventType)){
            specialFocusService.addSpecialFocusInfo(param);
        }else{
            specialFocusService.delSpecialFocusInfo(param);
        }
    }
}
