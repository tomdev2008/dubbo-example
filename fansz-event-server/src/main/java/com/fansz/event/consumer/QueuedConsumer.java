package com.fansz.event.consumer;

import com.fansz.event.consumer.support.IEventConsumer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.utils.CollectionTools;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从kafka队列读取消息,并根据消息的类型调用不同的消费者
 */
public class QueuedConsumer implements Closeable, ConsumerRebalanceListener, Runnable {
    private Logger logger = LoggerFactory.getLogger(QueuedConsumer.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);


    private final Map<String, IEventConsumer> CONSUMER_MAP = new ConcurrentHashMap<>();

    private final List<String> eventList = new ArrayList<>();

    private KafkaConsumer consumer;

    private CountDownLatch shutdownLatch = new CountDownLatch(1);


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
            consumer.subscribe(eventList, this);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    if (CollectionTools.isNullOrEmpty(partitionRecords)) continue;
                    boolean result = onRecordsReceived(records);
                    if (result) {
                        long lastoffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastoffset)));
                    }
                }
            }
        } catch (WakeupException e) {
            // ignore, we are closing
        } finally {
            consumer.close();
            shutdownLatch.countDown();
        }
    }

    public void close() {
        boolean interrupted = false;
        try {
            consumer.wakeup();
            while (true) {
                try {
                    shutdownLatch.await();
                    return;
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }
    }

    private boolean onRecordsReceived(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            logger.debug("received event,content:{}", record);
            IEventConsumer consumer = CONSUMER_MAP.get(record.key());
            if (consumer != null) {
                try {
                    consumer.onEvent(record);
                } catch (Exception e) {
                    logger.error("process consumer record encounter error", e);
                    logger.error("record key:{},record value:{}", record.key(), record.value());
                }
            } else {
                logger.warn("event type {} doesn't have consumer", record.key());
            }
        }
        return true;
    }

    // save the offsets in an external store
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        //TODO:
        logger.info("rebalance operation begin,partitions={}", partitions);
    }

    // read the offsets from an external store
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        //TODO:
        logger.info("partition re-assignment complete,partitions={}", partitions);
    }

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

}