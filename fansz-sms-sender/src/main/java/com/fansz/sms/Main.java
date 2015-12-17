package com.fansz.sms;

import com.fansz.sms.listener.KafkaConsumerRunner;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by allan on 15/11/27.
 */
public class Main {
    public static void main(String[] args) {
        KafkaConsumerRunner kafkaConsumerRunner = null;
        try {
            ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-sms.xml");
            ac.start();
            kafkaConsumerRunner = (KafkaConsumerRunner) ac.getBean("kafkaConsumerRunner");
            kafkaConsumerRunner.start();
        } finally {
            kafkaConsumerRunner.shutdown();
        }
    }
}
