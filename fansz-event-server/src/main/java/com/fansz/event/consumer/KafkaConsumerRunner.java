package com.fansz.event.consumer;

import com.fansz.event.consumer.support.IEventConsumer;
import com.fansz.event.exception.EventConsumerException;
import com.fansz.event.type.AsyncEventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从kafka队列读取消息,并发送短信
 */
public class KafkaConsumerRunner implements Runnable {

    private final AtomicBoolean closed = new AtomicBoolean(false);

    private Logger logger = LoggerFactory.getLogger(KafkaConsumerRunner.class);

    private final Map<String, IEventConsumer> CONSUMER_MAP = new ConcurrentHashMap<>();

    private final List<String> eventList = new ArrayList<>();

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        Map<String, IEventConsumer> m = applicationContext.getBeansOfType(IEventConsumer.class);
        for (IEventConsumer consumer : m.values()) {
            CONSUMER_MAP.put(consumer.getEventType().getCode(), consumer);
        }

        for (AsyncEventType aet : AsyncEventType.values()) {
            eventList.add(aet.getName());
        }
    }

    public void run() {
        try {
            consumer.subscribe(eventList);
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                if (records.isEmpty()) {
                    Thread.sleep(2000);
                } else if (processRecords(records)) {
                    consumer.commitSync();
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) {
                throw new EventConsumerException("event consumer thread error", e);
            }
        } catch (InterruptedException e) {
            logger.warn("thread is interrupted!");
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }

    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }

    private boolean processRecords(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            CONSUMER_MAP.get(record.key()).onEvent(record);
        }
        return true;
    }


}