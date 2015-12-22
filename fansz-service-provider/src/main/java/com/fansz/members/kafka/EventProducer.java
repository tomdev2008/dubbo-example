package com.fansz.members.kafka;


import com.fansz.members.model.event.AsyncEventType;
import com.fansz.pub.utils.JsonHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by allan on 15/12/16.
 */
@Component("eventProducer")
public class EventProducer {

    @Resource(name = "kafkaProp")
    private Properties kafkaProp;

    Producer<String, String> producer;

    @PostConstruct
    private void init() {
        producer = new KafkaProducer(kafkaProp);
    }

    public void produce(AsyncEventType event, Object val) {
        producer.send(new ProducerRecord<String, String>(event.getName(), event.getCode(), JsonHelper.convertObject2JSONString(val)));

    }

    public void close() {
        if (producer != null) producer.close();
    }
}
