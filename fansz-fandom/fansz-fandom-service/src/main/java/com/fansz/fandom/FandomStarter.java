package com.fansz.fandom;

import com.fansz.fandom.service.FandomService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by allan on 15/11/20.
 */
public class FandomStarter {
    private static CountDownLatch STOP = new CountDownLatch(1);
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        ac.start();
        FandomService fandomService=ac.getBean(FandomService.class);
        fandomService.init();
        try {
            STOP.await();            //等待服务关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fandom service stop running!");
        }
    }
}
