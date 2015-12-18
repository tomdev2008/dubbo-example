package com.fansz.members.api.service.impl;

import com.fansz.members.api.model.SmsMessage;
import com.fansz.members.api.model.VerifyCodeModel;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.kafka.MessageProducer;
import com.fansz.members.redis.JedisTemplate;
import com.fansz.members.redis.support.JedisCallback;
import com.fansz.members.tools.Constants;
import com.fansz.members.tools.VerifyCodeGenerator;
import com.fansz.members.tools.VerifyCodeType;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

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
    private JedisTemplate jedisTemplate;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Resource(name = "verifyCodeGenerator")
    private VerifyCodeGenerator verifyCodeGenerator;

    @Resource(name = "messageProducer")
    private MessageProducer messageProducer;

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
                if (exists == 0) {
                    throw new ApplicationException(Constants.MOBILE_NOT_FOUND, "Mobile does't belong to you");

                }
                template = smsProperties.getProperty("template.verify.reset");
                break;
        }

        return createVerifyCode(mobile, verifyCodeType.getName(), template);
    }


    private boolean createVerifyCode(final String mobile, final String key, final String template) {
        final Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put(key + ".verifyCode", verifyCodeGenerator.getIdentifyCode());

        long currentTime = System.currentTimeMillis();
        long expiredTime = currentTime + validPeriod * 60 * 1000;
        verifyMap.put(key + ".createTime", currentTime + "");
        verifyMap.put(key + ".expiredTime", expiredTime + "");
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                String createTime = jedis.hget(Constants.VERIFY_KEY + mobile, key + ".createTime");

                if (!isAllowed(createTime)) {
                    throw new ApplicationException(Constants.INTERVAL_IS_TOO_SHORT, "Interval is too short");
                }

                jedis.hmset(Constants.VERIFY_KEY + mobile, verifyMap);
                return true;
            }
        });


        //通过队列,异步方式发送短信
        String messgeContent = String.format(template, verifyMap.get(key + ".verifyCode"), validPeriod);
        //Send Message
        SmsMessage sms = new SmsMessage(messgeContent, mobile);
        String message = JsonHelper.convertObject2JSONString(sms);
        messageProducer.produce(sms.getMobile(), message);
        return true;
    }

    private boolean isAllowed(String createTime) {
        if (createTime == null || createTime.trim().length() == 0) {
            return true;
        }
        return System.currentTimeMillis() - Long.valueOf(createTime) >= sendInterval * 60 * 1000;//单位为分钟
    }

    public VerifyCodeModel queryVerifyCode(final String mobile, VerifyCodeType verifyCodeType) {
        Map<String, String> verifyMap = jedisTemplate.execute(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(Jedis jedis) throws Exception {
                return jedis.hgetAll(Constants.VERIFY_KEY + mobile);
            }
        });

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
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.hdel(Constants.VERIFY_KEY + mobile, verifyCodeName + ".verifyCode", verifyCodeName + ".createTime", verifyCodeName + ".expiredTime");
                return true;
            }
        });

    }

    @Override
    public boolean isValid(VerifyCodeModel verifyCodeEntity) {
        return verifyCodeEntity.getExpireTime() > System.currentTimeMillis();
    }
}
