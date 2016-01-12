package com.fansz.push.sender.ios;

import com.fansz.apns.PushManager;
import com.fansz.apns.config.ApnsEnvironment;
import com.fansz.apns.config.PushManagerConfiguration;
import com.fansz.apns.listener.ExpiredTokenListener;
import com.fansz.apns.listener.RejectedNotificationListener;
import com.fansz.apns.model.SimpleApnsPushNotification;
import com.fansz.apns.util.SSLContextUtil;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class PushManagerFactory implements FactoryBean<PushManager>, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(PushManagerFactory.class);

    private String pathToPKCS12File;

    private String keystorePassword;

    private NioEventLoopGroup group;

    private RejectedNotificationListener<SimpleApnsPushNotification> rejectedListener;

    private ExpiredTokenListener iosExpiredTokenListener;

    private PushManager<SimpleApnsPushNotification> pushManager;

    private boolean productionMode = false;

    @Override
    public PushManager getObject() throws Exception {
        logger.debug("begin to initialize pushManager instance");
        group = new NioEventLoopGroup();
        pushManager = new PushManager<SimpleApnsPushNotification>(
                productionMode ? ApnsEnvironment.getProductionEnvironment() : ApnsEnvironment.getSandboxEnvironment(),
                SSLContextUtil.createDefaultSSLContext(pathToPKCS12File, keystorePassword),
                group, // Optional:custom event loop group
                null, // Optional: custom ExecutorService for calling listeners
                null, // Optional: custom BlockingQueue implementation
                new PushManagerConfiguration(), "ios pushManager");
        pushManager.registerRejectedNotificationListener(rejectedListener);
        pushManager.registerExpiredTokenListener(iosExpiredTokenListener);
        return pushManager;
    }

    @Override
    public Class<?> getObjectType() {
        return PushManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void destroy() throws Exception {
        if (group != null) {
            group.shutdownGracefully();
        }
        if (!pushManager.isShutDown()) {
            try {
                pushManager.shutdown();
            } catch (InterruptedException e) {
                logger.error("PushManagerFactory InterruptedException when destroy", e);
            }
        }
    }


    public void setPathToPKCS12File(String pathToPKCS12File) {
        this.pathToPKCS12File = pathToPKCS12File;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public void setRejectedListener(RejectedNotificationListener<SimpleApnsPushNotification> rejectedListener) {
        this.rejectedListener = rejectedListener;
    }

    public void setIosExpiredTokenListener(ExpiredTokenListener iosExpiredTokenListener) {
        this.iosExpiredTokenListener = iosExpiredTokenListener;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }
}
