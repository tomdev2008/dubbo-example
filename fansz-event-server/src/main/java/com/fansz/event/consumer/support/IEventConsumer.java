package com.fansz.event.consumer.support;

import com.fansz.event.type.AsyncEventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Created by allan on 15/12/21.
 */
public interface IEventConsumer {
    final static Long FRIEND_LIMIT = 500l;

    void onEvent(ConsumerRecord<String, String> record);

    AsyncEventType getEventType();
}
