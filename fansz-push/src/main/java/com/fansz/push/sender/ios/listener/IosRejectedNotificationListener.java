package com.fansz.push.sender.ios.listener;

import com.alibaba.fastjson.JSON;
import com.fansz.apns.PushManager;
import com.fansz.apns.listener.RejectedNotificationListener;
import com.fansz.apns.model.SimpleApnsPushNotification;
import com.fansz.apns.support.RejectedNotificationReason;
import com.fansz.apns.util.TokenUtil;
import com.fansz.pub.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * APNS对于推送的消息，如果有失败的，会返回错误消息；如果推送到APNS平台成功，则不返回任何结果
 *
 * @author allan
 */
@Component("iosRejectedNotificationListener")
public class IosRejectedNotificationListener implements RejectedNotificationListener<SimpleApnsPushNotification> {

    private Logger logger = LoggerFactory.getLogger("service.sender");

    @Override
    public void handleRejectedNotification(PushManager<? extends SimpleApnsPushNotification> pushManager,
            SimpleApnsPushNotification notification, RejectedNotificationReason rejectionReason) {
        logger.debug("接收到APNS返回的错误消息，{}", notification);
        String deviceToken = TokenUtil.tokenBytesToString(notification.getToken());
        Map<String, Object> mapObj = JSON.parseObject(notification.getPayload(), Map.class);
        Object obj = mapObj.get("extras");
        if (obj != null) {
            Map<String, Object> extras = (Map)obj;
            String messageId = (String)extras.get("messageId");
            if (StringTools.isNotBlank(messageId)) {
                return;
            }
        }
        logger.warn("APNS返回的错误消息并没有得到完善处理，请检查相应逻辑");
    }

}
