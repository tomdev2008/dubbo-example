package com.fansz.feeds;

import com.fansz.service.extension.JacksonConfig;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/12/24.
 */
public class FeedStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-auth.xml");
        ac.start();
        ResteasyProviderFactory.getInstance().register(JacksonConfig.class);
    }
}
