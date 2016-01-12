package com.fansz.push.sender.android;

import com.fansz.push.sender.model.NotifyMessageVO;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.event.impl.BusinessEventProducer;
import com.fansz.push.server.session.ClientSession;
import com.fansz.push.server.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.TimerTask;

/**
 * 发送消息给Android App
 * 
 * @author Administrator
 */
@Component("androidSendNotificationWorker")
public class AndroidSendNotificationWorker extends TimerTask{

    private Logger logger = LoggerFactory.getLogger("service.sender");


    @Autowired
    private SessionManager sessionManager;

    @Resource(name = "businessEventProducer")
    protected BusinessEventProducer businessEventProducer;


    /**
     * 从kafka队列获取数据,推送到APP端
     */
    @Override
    public void run() {
        logger.debug("开始给Android应用推送消息");
        String sessionId=null;
        ClientSession session=sessionManager.getSession(sessionId);
        NotifyMessageVO messageObj = new NotifyMessageVO("", "", sessionId);
        businessEventProducer.publish(EventType.PUSH, messageObj, session);
        logger.debug("Android应用推送消息完毕");
    }

}
