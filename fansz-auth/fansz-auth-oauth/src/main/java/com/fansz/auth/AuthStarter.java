package com.fansz.auth;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by allan on 15/12/23.
 */
public class AuthStarter {
    private static CountDownLatch STOP = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
        try {
            STOP.await();            //等待服务关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("auth service stop running!");
        }
    }
}
