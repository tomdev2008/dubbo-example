package com.fansz.push.sender.ios;

import com.fansz.apns.PushManager;
import com.fansz.apns.model.SimpleApnsPushNotification;
import com.fansz.push.server.event.impl.BusinessEventProducer;
import com.fansz.push.server.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * 处理IOS的消息推送
 *
 * @author Administrator
 */
@Component("iosSendNotificationWorker")
public class IosSendNotificationWorker extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(IosSendNotificationWorker.class);

    /**
     * 定时方法，取出IOS的APP列表，循环处理每个APP的待发送消息
     */
    @Override
    public void run() {
        logger.debug("开始给Ios应用推送消息");

        logger.debug("Ios应用推送消息完毕");
    }


    private SessionManager sessionManager;


    protected BusinessEventProducer businessEventProducer;

    private PushManager<SimpleApnsPushNotification> pushManager;

}
