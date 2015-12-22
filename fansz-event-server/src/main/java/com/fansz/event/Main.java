package com.fansz.event;

import com.fansz.event.consumer.KafkaConsumerRunner;
import com.fansz.event.exception.SmsConsumerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 短消息发送线程启动入口
 */
public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        KafkaConsumerRunner kafkaConsumerRunner = null;
        ClassPathXmlApplicationContext ac = null;
        try {
            ac= new ClassPathXmlApplicationContext("applicationContext-sms.xml");
            ac.start();
            kafkaConsumerRunner = (KafkaConsumerRunner) ac.getBean("kafkaConsumerRunner");
            kafkaConsumerRunner.start();
        } catch (SmsConsumerException e) {
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
