package com.fansz.auth.service.impl;

import com.fansz.auth.model.VerifyCodeModel;
import com.fansz.auth.model.VerifyCodeType;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.auth.utils.VerifyCodeGenerator;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.event.model.SmsEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.UserTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 验证码服务层实现
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Resource(name = "verifyCodeGenerator")
    private VerifyCodeGenerator verifyCodeGenerator;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Resource(name = "smsProperties")
    private Properties smsProperties;

    @Value("${code.valid.interval}")
    private Integer validPeriod;

    @Value("${code.send.interval}")
    private Integer sendInterval;

    /**
     * 获取验证码,重置密码时使用
     *
     * @param mobile 手机号码
     */
    @Override
    public ErrorCode createVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        String userSn = userTemplate.getSnByMobile(mobile);
        String template = "";
        switch (verifyCodeType) {
            case REGISTER://用户注册时,要求号码未被使用
                if (StringTools.isNotBlank(userSn)) {
                    return ErrorCode.MOBILE_IS_USED;
                }
                template = smsProperties.getProperty("template.verify.register");
                break;
            case RESET://重置密码时,要求号码已经存在
                if (StringTools.isBlank(userSn)) {
                    return ErrorCode.MOBILE_NOT_FOUND;

                }
                template = smsProperties.getProperty("template.verify.reset");
                break;
        }

        return createVerifyCode(mobile, verifyCodeType.getName(), template);
    }


    private ErrorCode createVerifyCode(final String mobile, final String key, final String template) {
        final Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put(key + ".verifyCode", verifyCodeGenerator.getIdentifyCode());

        long currentTime = System.currentTimeMillis();
        long expiredTime = currentTime + validPeriod * 60 * 1000;
        verifyMap.put(key + ".createTime", currentTime + "");
        verifyMap.put(key + ".expiredTime", expiredTime + "");
        String createTime = userTemplate.getVerifyCodeCreateTime(mobile, key);
        if (!isAllowed(createTime)) {
            return ErrorCode.INTERVAL_IS_TOO_SHORT;
        }
        userTemplate.saveVerifyCode(mobile, verifyMap);

        //通过队列,异步方式发送短信
        String messgeContent = String.format(template, verifyMap.get(key + ".verifyCode"), validPeriod);
        //Send Message
        SmsEvent sms = new SmsEvent(messgeContent, mobile);
        eventProducer.produce(AsyncEventType.SEND_SMS, sms);
        return ErrorCode.SUCCESS;
    }

    private boolean isAllowed(String createTime) {
        if (createTime == null || createTime.trim().length() == 0) {
            return true;
        }
        return System.currentTimeMillis() - Long.valueOf(createTime) >= sendInterval * 60 * 1000;//单位为分钟
    }

    public VerifyCodeModel queryVerifyCode(final String mobile, VerifyCodeType verifyCodeType) {
        Map<String, String> verifyMap = userTemplate.queryVerifyCode(mobile);
        if (verifyMap != null) {
            String verifyCode = (String) verifyMap.get(verifyCodeType.getName() + ".verifyCode");
            String expiredTime = (String) verifyMap.get(verifyCodeType.getName() + ".expiredTime");
            if (StringTools.isNotBlank(verifyCode)) {
                VerifyCodeModel verifyCodeEntity = new VerifyCodeModel(verifyCode, Long.valueOf(expiredTime));
                return verifyCodeEntity;
            }
        }
        return null;
    }

    @Override
    public void removeVerifyCode(final String mobile, VerifyCodeType verifyCodeType) {
        final String verifyCodeName = verifyCodeType.getName();
        userTemplate.removeVerifyCode(mobile, verifyCodeName);
    }

    @Override
    public boolean isValid(VerifyCodeModel verifyCodeEntity) {
        return verifyCodeEntity.getExpireTime() > System.currentTimeMillis();
    }
}
