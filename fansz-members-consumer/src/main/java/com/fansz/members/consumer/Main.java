package com.fansz.members.consumer;

import com.fansz.members.api.AccountApi;
import com.fansz.members.model.account.RegisterParam;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        AccountApi us = ac.getBean(AccountApi.class);
        RegisterParam userParameters=new RegisterParam();
        userParameters.setLoginAccount("demo");
        userParameters.setPassword("demo");
        userParameters.setVerifyCode("demo");
        us.register(userParameters);
    }
}
