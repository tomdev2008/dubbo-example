package com.fansz.sms;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/27.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-sms.xml");
        ac.start();
    }
}
