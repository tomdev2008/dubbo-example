package com.fansz.relations;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by allan on 15/12/24.
 */
public class RelationStarter {
    private static CountDownLatch STOP = new CountDownLatch(1);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-relations.xml");
        ac.start();
        try {
            STOP.await();            //等待服务关闭
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("relation service stop running!");
        }
    }
}
