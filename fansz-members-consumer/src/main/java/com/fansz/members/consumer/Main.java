package com.fansz.members.consumer;

import com.fansz.members.api.AuthApi;
import com.fansz.members.model.UserParameters;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        AuthApi us = ac.getBean(AuthApi.class);
        UserParameters userParameters=new UserParameters();
        userParameters.setUserAccount("demo");
        userParameters.setPassword("demo");
        userParameters.setVerifyCode("demo");
        userParameters.setAppKey("com.fansz.app");
        us.login(userParameters);
    }
}
