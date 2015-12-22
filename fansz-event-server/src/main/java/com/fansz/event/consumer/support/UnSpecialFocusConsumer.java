package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.members.api.SpecialFocusApi;
import com.fansz.members.model.event.AsyncEventType;
import com.fansz.members.model.event.SpecialFocusEvent;
import com.fansz.members.model.event.UnSpecialFocusEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by allan on 15/12/21.
 */
@Component("unSpecialFocusConsumer")
public class UnSpecialFocusConsumer implements IEventConsumer {

    @Resource(name = "specialFocusProvider")
    private com.fansz.members.api.SpecialFocusApi SpecialFocusApi;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        UnSpecialFocusEvent sfe = JSON.parseObject(record.value(), UnSpecialFocusEvent.class);
        SpecialFocusApi.delSpecialFocusInfo(sfe.getParam());
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.UN_SPECIAL_FOCUS;
    }
}
