package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.sender.HttpSender;
import com.fansz.members.model.event.AsyncEventType;
import com.fansz.members.model.event.SmsEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短消息发送事件消费者
 */
@Component("smsConsumer")
public class SmsConsumer implements IEventConsumer {
    private Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    @Value("${event.url}")
    private String url;

    @Value("${event.account}")
    private String account;

    @Value("${event.pwd}")
    private String pswd;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        try {
            SmsEvent smsMessage = JSON.parseObject(record.value(), SmsEvent.class);
            String returnString = HttpSender.batchSend(url, account, pswd, smsMessage.getMobile(), smsMessage.getContent(), true, null, null);
            logger.debug("event provider return code is :{}", returnString);
        } catch (Exception e) {
            logger.error("短消息发送失败", e);
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.SEND_SMS;
    }
}
