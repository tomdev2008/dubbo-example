package com.fansz.token;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by allan on 16/1/20.
 */
public class TokenStarter {
    private static CountDownLatch STOP = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-token.xml");
        ac.start();
        try {
            STOP.await();            //等待服务关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("token service stop running!");
        }
    }
}
