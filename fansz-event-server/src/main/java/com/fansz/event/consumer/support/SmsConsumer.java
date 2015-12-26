package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.event.model.SmsEvent;
import com.fansz.event.sender.HttpSender;
import com.fansz.event.type.AsyncEventType;
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

    @Value("${sms.url}")
    private String url;

    @Value("${sms.account}")
    private String account;

    @Value("${sms.pwd}")
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
