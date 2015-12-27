package com.fansz.auth;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/12/23.
 */
public class AuthStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
    }
}
