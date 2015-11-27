package com.fansz.members.api.service.impl;

import com.fansz.members.api.model.SmsMessage;
import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.api.utils.VerifyCodeType;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.tools.UUIDTools;
import com.fansz.members.tools.VerifyCodeGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 验证码服务层实现
 */
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
        int exists = userEntityMapper.isExists(mobile);
        String template = "";
        switch (verifyCodeType) {
            case REGISTER://用户注册时,要求号码未被使用
                if (exists > 0) {
                    throw new ApplicationException(Constants.MOBILE_NOT_FOUND, "手机号码不存在");
                }
                template = smsProperties.getProperty("template.verify.register");
                break;
            case RESET://重置密码时,要求号码已经存在
                if (exists == 0) {
                    throw new ApplicationException(Constants.MOBILE_IS_USED, "手机号码已经被使用");
                }
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
            redisTemplate.boundHashOps(key).putAll(verifyMap);


            //通过队列,异步方式发送短信
            String messgeContent = String.format(template, verifyMap.get(key + ".verifyCode"), validPeriod);
            //Send Message
            SmsMessage sms = new SmsMessage(messgeContent, mobile);
            redisTemplate.convertAndSend("sms", sms);
            //redisTemplate.boundListOps("sms").leftPush(JsonHelper.toString(sms));
            return true;
        }
        return false;
    }

    private boolean isAllowed(String createTime) {
        if (createTime != null && createTime.trim().length() > 0) {
            if (System.currentTimeMillis() - Long.valueOf(createTime) >= 2 * 60 * 1000) {//2分钟内不允许多次发送验证码
                return true;
            }
        }
        return false;
    }

    public VerifyCode queryVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        String verifyStr = (String) redisTemplate.boundHashOps(Constants.VERIFY_KEY).get(verifyCodeType.getName() + ".verifyCode");
        if (verifyStr == null || verifyStr.trim().length() == 0) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(verifyStr, VerifyCode.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    @Override
    public void removeVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        redisTemplate.boundHashOps(Constants.VERIFY_KEY + mobile).delete(verifyCodeType.getName() + ".verifyCode", verifyCodeType.getName() + ".createTime", verifyCodeType.getName() + ".expiredTime");
    }
}
