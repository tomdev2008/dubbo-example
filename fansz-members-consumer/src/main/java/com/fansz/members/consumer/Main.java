package com.fansz.members.consumer;

import com.fansz.members.api.AccountApi;
import com.fansz.members.model.account.RegisterParam;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        ac.start();
        HttpRequestRouter httpRequestRouter = ac.getBean(HttpRequestRouter.class);
        NettyHttpService service=null;
        try {
            NettyHttpService.builder().setPort(2000).setHttpRequestRouter(httpRequestRouter).build().startUp();
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
