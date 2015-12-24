package com.fansz.service.consumer;

import com.fansz.service.consumer.server.HttpRequestRouter;
import com.fansz.service.consumer.server.NettyHttpService;
import com.fansz.service.extension.JacksonConfig;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        ac.start();
        ResteasyProviderFactory.getInstance().register(JacksonConfig.class);
        HttpRequestRouter httpRequestRouter = ac.getBean(HttpRequestRouter.class);
        NettyHttpService service=null;
        try {
            service=NettyHttpService.builder().setPort(3000).setHttpRequestRouter(httpRequestRouter).build();
            service.startUp();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                service.shutDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
