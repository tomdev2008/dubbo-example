package com.fansz.members.api.service.impl;

import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.repository.UserRepository;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.tools.UUIDTools;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 15/11/26.
 */
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserEntityMapper userEntityMapper;

    private final static String VERIFY_KEY = "verify:";

    /**
     * 获取验证码,重置密码时使用
     *
     * @param mobile 手机号码
     */
    @Override
    public boolean getPasswordIdentifyCode(String mobile) {
        if (userEntityMapper.isExists(mobile) == 0) {//用户不存在
            return false;
        }

        createVerifyCode(mobile,"reset");
        return true;
    }

    @Override
    public boolean getRegisterIdentifyCode(String mobile) {
        if (userEntityMapper.isExists(mobile) != 0) {//用户已经存在
            return false;
        }
        createVerifyCode(mobile,"register");
        return true;
    }

    private void createVerifyCode(String mobile,String key) {
        String createTime = (String) redisTemplate.boundHashOps(VERIFY_KEY+mobile).get(key + ".createTime");
        if (createTime == null || createTime.trim().length() == 0) {//redis中不存在纪录
            Map<String, String> verifyMap = new HashMap<String, String>();
            verifyMap.put(key + ".verifyCode", UUIDTools.getUniqueId());

            long currentTime = System.currentTimeMillis();
            long expiredTime = currentTime + 2 * 60 * 1000;
            verifyMap.put(key + ".createTime", currentTime + "");
            verifyMap.put(key + ".expiredTime", expiredTime + "");
            redisTemplate.boundHashOps(key).putAll(verifyMap);
        }


        //通过队列,异步方式发送短信
        String messgeContent = "";
        //Send Message
        redisTemplate.boundListOps("sms").leftPush(messgeContent);

    }

    public VerifyCode getVerifyCode(String mobile, String key) {
        String verifyStr = (String) redisTemplate.boundHashOps(VERIFY_KEY).get(key + ".verifyCode");
        if (verifyStr == null || verifyStr.trim().length() == 0){
            return null;
        }
        try {
            return new ObjectMapper().readValue(verifyStr,VerifyCode.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    @Override
    public void removeVerifyCode(String mobile, String key) {
        redisTemplate.boundHashOps(VERIFY_KEY+mobile).delete(key+".verifyCode",key+".createTime",key+".expiredTime");
    }
}
