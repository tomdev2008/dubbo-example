package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.members.api.SpecialFocusApi;
import com.fansz.members.model.event.AsyncEventType;
import com.fansz.members.model.event.SmsEvent;
import com.fansz.members.model.event.SpecialFocusEvent;
import com.fansz.members.model.event.UnSpecialFocusEvent;
import com.fansz.members.model.specialfocus.SpecialFocusParam;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by allan on 15/12/21.
 */
@Component("specialFocusConsumer")
public class SpecialFocusConsumer implements IEventConsumer {

    @Resource(name = "specialFocusProvider")
    private SpecialFocusApi SpecialFocusApi;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        SpecialFocusEvent sfe = JSON.parseObject(record.value(), SpecialFocusEvent.class);
        SpecialFocusApi.addSpecialFocusInfo(sfe.getParam());
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.SPECIAL_FOCUS;
    }
}
