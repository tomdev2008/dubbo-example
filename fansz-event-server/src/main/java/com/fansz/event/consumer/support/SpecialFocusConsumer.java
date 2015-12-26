package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.model.SpecialFocusEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.fandom.api.SpecialFocusApi;
import com.fansz.fandom.model.specialfocus.SpecialFocusParam;
import com.fansz.pub.utils.BeanTools;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by allan on 15/12/21.
 */
@Component("specialFocusConsumer")
public class SpecialFocusConsumer implements IEventConsumer {

    @Resource(name = "specialFocusProvider")
    private SpecialFocusApi specialFocusApi;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        SpecialFocusEvent sfe = JSON.parseObject(record.value(), SpecialFocusEvent.class);
        SpecialFocusParam param= BeanTools.copyAs(sfe,SpecialFocusParam.class);
        specialFocusApi.addSpecialFocusInfo(param);
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.SPECIAL_FOCUS;
    }
}
