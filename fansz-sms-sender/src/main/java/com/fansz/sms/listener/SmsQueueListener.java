package com.fansz.sms.listener;

import com.alibaba.fastjson.JSON;
import com.fansz.sms.model.SmsMessage;
import com.fansz.sms.sender.HttpSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 短信发送服务
 */
public class SmsQueueListener {
    private Logger logger = LoggerFactory.getLogger(SmsQueueListener.class);

    @Resource(name="smsProperties")
    private Properties smsProperties;

    public void handleMessage(String message, String channel) {
        String url = smsProperties.getProperty("sms.url");
        String account = smsProperties.getProperty("sms.account");
        String pswd = smsProperties.getProperty("sms.pwd");

        try {
            SmsMessage smsMessage = JSON.parseObject(message, SmsMessage.class);
            String returnString = HttpSender.batchSend(url, account, pswd, smsMessage.getMobile(), smsMessage.getContent(), true, null, null);
            System.out.println(returnString);
            logger.debug(returnString);
        } catch (Exception e) {
            logger.error("短消息发送失败", e);
        }
    }
}
