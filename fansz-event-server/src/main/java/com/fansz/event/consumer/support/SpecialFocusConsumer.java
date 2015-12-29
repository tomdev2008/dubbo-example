package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.SpecialFocus;
import com.fansz.db.repository.SpecialFocusDAO;
import com.fansz.event.model.SpecialFocusEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.utils.BeanTools;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 15/12/21.
 */
@Component("specialFocusConsumer")
public class SpecialFocusConsumer implements IEventConsumer {

    @Autowired
    private SpecialFocusDAO specialFocusDAO;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        SpecialFocusEvent sfe = JSON.parseObject(record.value(), SpecialFocusEvent.class);
        SpecialFocus entity= BeanTools.copyAs(sfe,SpecialFocus.class);
        entity.setMemberSn(sfe.getCurrentSn());
        specialFocusDAO.save(entity);
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.SPECIAL_FOCUS;
    }
}
