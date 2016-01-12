package com.fansz;

import com.fansz.push.sender.SendNotificationTimer;
import com.fansz.push.server.MessageServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class PushStarter {

    private PushStarter() {

    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 1) {
            System.out.println("请指定服务器IP地址");
            return;
        }
        String serverIp = args[0];
        System.out.println("服务器IP地址:" + serverIp);
        new PushStarter().start();
    }

    private void start() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MessageServer msgServer = context.getBean(MessageServer.class);

        // 启动推送线程服务
        SendNotificationTimer sendNotificationTimer = (SendNotificationTimer)context.getBean("sendNotificationTimer");
        sendNotificationTimer.start();

        // 启动消息服务器
        msgServer.start();

    }
}
