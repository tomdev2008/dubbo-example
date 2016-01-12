package com.fansz.push.sender;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("sendNotificationTimer")
public class SendNotificationTimer {
    private Logger logger = LoggerFactory.getLogger(SendNotificationTimer.class);

    private Timer timer = new Timer("SendNotificationTimer", true);

    private int delay = 60 * 1000;

    @Resource(name = "androidSendNotificationWorker")
    private TimerTask androidSendNotificationWorker;

    @Resource(name = "iosSendNotificationWorker")
    private TimerTask iosSendNotificationWorker;

    @Resource(name = "androidDataSyncWorker")
    private TimerTask androidDataSyncWorker;

    @Resource(name = "iosDataSyncWorker")
    private TimerTask iosDataSyncWorker;

    @Autowired
    private ApplicationContext applicationContext;

    public void start() {
        timer.scheduleAtFixedRate(androidSendNotificationWorker, delay, 1000 * 60);// 1分钟
        timer.scheduleAtFixedRate(iosSendNotificationWorker, delay, 1000 * 60);// 1分钟
        timer.scheduleAtFixedRate(androidDataSyncWorker, delay, 1000 * 60 * 10);// 10分钟
        timer.scheduleAtFixedRate(iosDataSyncWorker, delay, 1000 * 60 * 10);// 10分钟
    }

    public void close() {
        if (timer != null) {
            timer.cancel();
        }
    }
}