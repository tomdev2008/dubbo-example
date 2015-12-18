package com.fansz.members.kafka;


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
@Component("messageProducer")
public class MessageProducer {

    @Resource(name = "kafkaProp")
    private Properties kafkaProp;

    Producer<String, String> producer;

    @PostConstruct
    private void init() {
        producer = new KafkaProducer(kafkaProp);
    }

    public void produce(String key, String val) {
        producer.send(new ProducerRecord<String, String>("sms", key, val));

    }

    public void close() {
        if (producer != null) producer.close();
    }
}
