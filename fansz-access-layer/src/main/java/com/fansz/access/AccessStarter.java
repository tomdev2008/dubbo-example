package com.fansz.access;

import com.fansz.access.server.HttpRequestRouter;
import com.fansz.http.server.NettyHttpService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by allan on 15/11/20.
 */
public class AccessStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-access.xml");
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