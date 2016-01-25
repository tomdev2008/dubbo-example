package com.fansz.event;

import com.fansz.event.consumer.QueuedConsumer;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.NetWorkTools;
import com.fansz.pub.utils.StringTools;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * 异步队列处理线程启动入口
 */
public class EventStarter {
    private final static Logger logger = LoggerFactory.getLogger(EventStarter.class);

    public static void main(String[] args) {
        String clientId = CollectionTools.isNotNullOrEmptyArray(args) ? args[0] : NetWorkTools.getLocalIpAddress();
        if (StringTools.isBlank(clientId)) {
            System.err.println("failed to get local ip address or can't find  clientId configuration ,system exit!");
            System.exit(0);
        }

        final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-event.xml");
        ac.start();

        Properties kafkaProp = (Properties) ac.getBean("kafkaProp");
        kafkaProp.setProperty("client.id", "consumer-" + clientId);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(kafkaProp);
        final QueuedConsumer queuedConsumer = (QueuedConsumer) ac.getBean("queuedConsumer");
        queuedConsumer.setConsumer(kafkaConsumer);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("system exit");
                queuedConsumer.close();
                ac.stop();
            }
        });

        queuedConsumer.run();

    }
}
