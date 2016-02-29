package com.fansz.access;

import com.fansz.access.server.HttpRequestRouter;
import com.fansz.http.server.NettyHttpService;
import com.fansz.http.server.ssl.SSLConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.Properties;

/**
 * Created by allan on 15/11/20.
 */
public class AccessStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-access.xml");
        ac.start();
        HttpRequestRouter httpRequestRouter = ac.getBean(HttpRequestRouter.class);
        System.setProperty("io.netty.initialSeedUniquifier", String.valueOf(System.currentTimeMillis()));
        NettyHttpService service = null;
        try {
            Properties prop = (Properties) ac.getBean("applicationProp");
            String keyStorePath = prop.getProperty("keystore.path");
            String keyStorePassword = prop.getProperty("keystore.password");
            File keyStore = new File(keyStorePath);
            SSLConfig sslConfig = SSLConfig.builder(keyStore, keyStorePassword).build();
            service = NettyHttpService.builder()/*.enableSSL(sslConfig)*/.setHttpRequestRouter(httpRequestRouter).setPort(3000).build();
            service.startUp();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.err.println("Access layer server stop running!");
            try {
                service.shutDown();
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
