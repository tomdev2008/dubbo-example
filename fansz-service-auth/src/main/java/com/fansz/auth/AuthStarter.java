package com.fansz.auth;

import com.fansz.service.extension.JacksonConfig;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/12/23.
 */
public class AuthStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
        ResteasyProviderFactory.getInstance().register(JacksonConfig.class);
    }
}
