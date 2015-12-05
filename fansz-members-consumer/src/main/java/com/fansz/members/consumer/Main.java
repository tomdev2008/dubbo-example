package com.fansz.members.consumer;

import com.fansz.members.consumer.server.BasicHttpResponder;
import com.fansz.members.consumer.server.HttpRequestRouter;
import com.fansz.members.consumer.server.NettyHttpService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
