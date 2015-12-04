package com.fansz.members;

import com.fansz.members.tools.JacksonConfig;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        ac.start();
        ResteasyProviderFactory.getInstance().register(JacksonConfig.class);
    }
}
