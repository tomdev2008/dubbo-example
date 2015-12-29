package com.fansz.event;

import com.fansz.event.consumer.KafkaConsumerRunner;
import com.fansz.event.exception.EventConsumerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 短消息发送线程启动入口
 */
public class EventStarter {
    private final static Logger logger = LoggerFactory.getLogger(EventStarter.class);

    public static void main(String[] args) {
        KafkaConsumerRunner kafkaConsumerRunner = null;
        ClassPathXmlApplicationContext ac = null;
        try {
            ac = new ClassPathXmlApplicationContext("applicationContext-event.xml");
            ac.start();
            kafkaConsumerRunner = (KafkaConsumerRunner) ac.getBean("kafkaConsumerRunner");
            ExecutorService executor = Executors.newFixedThreadPool(4);
            executor.submit(kafkaConsumerRunner);
        } catch (EventConsumerException e) {
            logger.error("SMS sending thread error,please restart the application!", e);
            if (kafkaConsumerRunner != null) {
                kafkaConsumerRunner.shutdown();
            }
            if (ac != null) {
                ac.close();
            }
        }
    }
}
