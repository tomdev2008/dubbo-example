package com.fansz.members.api.service.impl;

import com.fansz.members.api.model.SmsMessage;
import com.fansz.members.api.model.VerifyCodeModel;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Resource(name = "smsProperties")
    private Properties smsProperties;

    @Resource(name = "verifyCodeGenerator")
    private VerifyCodeGenerator verifyCodeGenerator;


    /**
     * 获取验证码,重置密码时使用
     *
     * @param mobile 手机号码
     */
    @Override
    public boolean createVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        int exists = userEntityMapper.isExistsMobile(mobile);
        String template = "";
        switch (verifyCodeType) {
            case REGISTER://用户注册时,要求号码未被使用
                if (exists > 0) {
                    throw new ApplicationException(Constants.MOBILE_IS_USED, "Mobile is used");
                }
                template = smsProperties.getProperty("template.verify.register");
                break;
            case RESET://重置密码时,要求号码已经存在
                /**if (exists == 0) {
                    throw new ApplicationException(Constants.MOBILE_NOT_FOUND, "Mobile does't belong to yours");

                }*/
                template = smsProperties.getProperty("template.verify.reset");
                break;
        }

        return createVerifyCode(mobile, verifyCodeType.getName(), template);
    }


    private boolean createVerifyCode(String mobile, String key, String template) {
        String createTime = (String) redisTemplate.boundHashOps(Constants.VERIFY_KEY + mobile).get(key + ".createTime");

        if (isAllowed(createTime)) {
            Map<String, String> verifyMap = new HashMap<>();
            verifyMap.put(key + ".verifyCode", verifyCodeGenerator.getIdentifyCode());

            long currentTime = System.currentTimeMillis();
            long validPeriod = Long.valueOf(smsProperties.getProperty("period.verify", "10"));//单位为分钟
            long expiredTime = currentTime + validPeriod * 60 * 1000;
            verifyMap.put(key + ".createTime", currentTime + "");
            verifyMap.put(key + ".expiredTime", expiredTime + "");
            redisTemplate.boundHashOps(Constants.VERIFY_KEY + mobile).putAll(verifyMap);


            //通过队列,异步方式发送短信
            String messgeContent = String.format(template, verifyMap.get(key + ".verifyCode"), validPeriod);
            //Send Message
            SmsMessage sms = new SmsMessage(messgeContent, mobile);
            String message = JsonHelper.toString(sms);
            redisTemplate.convertAndSend("sms", message);
            return true;
        }
        return false;
    }

    private boolean isAllowed(String createTime) {
        if (createTime == null || createTime.trim().length() == 0) {
            return true;
        }
        return System.currentTimeMillis() - Long.valueOf(createTime) >= 2 * 60 * 1000;
    }

    public VerifyCodeModel queryVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        Map<Object, Object> verifyMap = redisTemplate.boundHashOps(Constants.VERIFY_KEY + mobile).entries();
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
    public void removeVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        redisTemplate.boundHashOps(Constants.VERIFY_KEY + mobile).delete(verifyCodeType.getName() + ".verifyCode", verifyCodeType.getName() + ".createTime", verifyCodeType.getName() + ".expiredTime");
    }

    @Override
    public boolean isValid(VerifyCodeModel verifyCodeEntity) {
        return verifyCodeEntity.getExpireTime()>System.currentTimeMillis();
    }
}
