package com.fansz.sms.listener;

import com.alibaba.fastjson.JSON;
import com.fansz.sms.model.SmsMessage;
import com.fansz.sms.sender.HttpSender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by allan on 15/12/16.
 */
public class KafkaConsumerRunner extends Thread {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private Logger logger = LoggerFactory.getLogger(KafkaConsumerRunner.class);

    @Autowired
    private KafkaConsumer consumer;

    @Value("sms.url")
    private String url;

    @Value("sms.account")
    private String account;

    @Value("sms.pwd")
    private String pswd;


    public void run() {
        consumer.subscribe(Arrays.asList("ms"));
        try {
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(1);
                sendSms(records);
                Thread.sleep(6000);
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }

    private void sendSms(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            try {
                SmsMessage smsMessage = JSON.parseObject(record.value(), SmsMessage.class);
                String returnString = HttpSender.batchSend(url, account, pswd, smsMessage.getMobile(), smsMessage.getContent(), true, null, null);
                System.out.println(returnString);
                logger.debug(returnString);
            } catch (Exception e) {
                logger.error("短消息发送失败", e);
            }
        }
    }
}