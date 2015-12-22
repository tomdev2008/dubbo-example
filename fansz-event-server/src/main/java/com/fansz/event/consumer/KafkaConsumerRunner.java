package com.fansz.event.consumer;

import com.fansz.event.consumer.support.IEventConsumer;
import com.fansz.event.exception.SmsConsumerException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从kafka队列读取消息,并发送短信
 */
public class KafkaConsumerRunner extends Thread {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerRunner.class);

    private final static Map<String, IEventConsumer> CONSUMER_MAP = new ConcurrentHashMap<>();

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        Map<String, IEventConsumer> m = applicationContext.getBeansOfType(IEventConsumer.class);
        for (IEventConsumer consumer : m.values()) {
            CONSUMER_MAP.put(consumer.getEventType().getName(), consumer);
        }
    }

    public void run() {
        consumer.subscribe(Arrays.asList("event"));
        try {
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(2000);
                processRecords(records);
                consumer.commitSync();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // Ignore exception if closing
            if (!closed.get()) {
                throw new SmsConsumerException("event sender thread error", e);
            }
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    private void processRecords(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            CONSUMER_MAP.get(record.key()).onEvent(record);
        }
    }


}