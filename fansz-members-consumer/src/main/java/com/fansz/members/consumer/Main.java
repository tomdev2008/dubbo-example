package com.fansz.members.consumer;

import com.fansz.members.consumer.server.BasicHttpResponder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-consumer.xml");
        ac.start();
        BasicHttpResponder.HttpRequestRouter httpRequestRouter = ac.getBean(BasicHttpResponder.HttpRequestRouter.class);
        BasicHttpResponder.NettyHttpService service=null;
        try {
            BasicHttpResponder.NettyHttpService.builder().setPort(2000).setHttpRequestRouter(httpRequestRouter).build().startUp();
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
