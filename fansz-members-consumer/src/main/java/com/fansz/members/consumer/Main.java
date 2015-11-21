package com.fansz.members.consumer;

import com.fansz.members.api.UserService;
import com.fansz.members.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        UserService us = ac.getBean(UserService.class);
        us.logout(12L);
    }
}
