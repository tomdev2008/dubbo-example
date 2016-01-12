package com.fansz.push.sender.ios.listener;

import com.fansz.apns.PushManager;
import com.fansz.apns.listener.ExpiredTokenListener;
import com.fansz.apns.model.ExpiredToken;
import com.fansz.apns.model.SimpleApnsPushNotification;
import com.fansz.apns.util.TokenUtil;
import com.fansz.pub.utils.CollectionTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component("iosExpiredTokenListener")
public class IosExpiredTokenListener implements ExpiredTokenListener<SimpleApnsPushNotification> {
    private Logger logger = LoggerFactory.getLogger("service.sender");

    @Override
    public void handleExpiredTokens(PushManager<? extends SimpleApnsPushNotification> pushManager,
            Collection<ExpiredToken> expiredTokens) {
        logger.debug("APNS Feedback返回失效的deviceToken,删除失效的token");
        if (!CollectionTools.isNullOrEmpty(expiredTokens)) {
            for (ExpiredToken expiredToken : expiredTokens) {
                String deviceToken = TokenUtil.tokenBytesToString(expiredToken.getToken());

            }
        }
        logger.debug("APNS Feedback返回失效的deviceToken,删除失效的token,done!");
    }

}
