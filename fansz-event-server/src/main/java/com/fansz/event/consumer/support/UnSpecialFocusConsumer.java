package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.model.UnSpecialFocusEvent;
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
@Component("unSpecialFocusConsumer")
public class UnSpecialFocusConsumer implements IEventConsumer {

    @Resource(name = "specialFocusProvider")
    private SpecialFocusApi specialFocusApi;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        UnSpecialFocusEvent sfe = JSON.parseObject(record.value(), UnSpecialFocusEvent.class);
        SpecialFocusParam param= BeanTools.copyAs(sfe,SpecialFocusParam.class);
        specialFocusApi.delSpecialFocusInfo(param);
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.UN_SPECIAL_FOCUS;
    }
}
