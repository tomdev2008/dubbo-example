package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.type.AsyncEventType;
import com.fansz.service.api.SpecialFocusApi;
import com.fansz.service.model.event.SpecialFocusEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
