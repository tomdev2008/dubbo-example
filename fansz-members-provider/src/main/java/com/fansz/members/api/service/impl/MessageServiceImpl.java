package com.fansz.members.api.service.impl;

import com.fansz.appservice.configuration.sms.SMSProperties;
import com.fansz.appservice.service.MessageService;
import com.fansz.appservice.utils.HttpSender;
import com.fansz.appservice.utils.MessageAccount;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by root on 15-11-3.
 */
@Service
@Log4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private SMSProperties smsProperties;

    @Override
    public void sendMessage(String mobile, String msg) {
        //发送消息（同步） TODO M2 改为异步机制
        try {
            String returnString = HttpSender.batchSend(MessageAccount.url, MessageAccount.account, MessageAccount.pwd, mobile, msg, false, "", "");
            log.info(returnString);
        }
        catch (Exception e) {
            log.info("发送失败");
            //TODO 如果发送失败，则进入错误发送队列（重发机制）
        }

    }
}
