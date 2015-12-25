package com.fansz.auth.service.impl;

import com.fansz.auth.entity.UserEntity;
import com.fansz.auth.model.VerifyCodeModel;
import com.fansz.auth.model.VerifyCodeType;
import com.fansz.auth.repository.UserRepository;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.auth.utils.VerifyCodeGenerator;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.service.constant.ErrorCode;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.event.SmsEvent;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    private UserRepository userRepository;

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

    public final static String VERIFY_KEY = "verify:";

    /**
     * 获取验证码,重置密码时使用
     *
     * @param mobile 手机号码
     */
    @Override
    public ErrorCode createVerifyCode(String mobile, VerifyCodeType verifyCodeType) {
        UserEntity user = userRepository.findByMobile(mobile);
        String template = "";
        switch (verifyCodeType) {
            case REGISTER://用户注册时,要求号码未被使用
                if (user != null) {
                    return ErrorCode.MOBILE_IS_USED;
                }
                template = smsProperties.getProperty("template.verify.register");
                break;
            case RESET://重置密码时,要求号码已经存在
                if (user == null) {
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
        ErrorCode result = jedisTemplate.execute(new JedisCallback<ErrorCode>() {
            @Override
            public ErrorCode doInRedis(Jedis jedis) throws Exception {
                String createTime = jedis.hget(VERIFY_KEY + mobile, key + ".createTime");

                if (!isAllowed(createTime)) {
                    return ErrorCode.INTERVAL_IS_TOO_SHORT;
                }

                jedis.hmset(VERIFY_KEY + mobile, verifyMap);
                return ErrorCode.SUCCESS;
            }
        });
        if (!ErrorCode.SUCCESS.equals(result)) {
            return result;
        }

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
        Map<String, String> verifyMap = jedisTemplate.execute(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(Jedis jedis) throws Exception {
                return jedis.hgetAll(VERIFY_KEY + mobile);
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
                jedis.hdel(VERIFY_KEY + mobile, verifyCodeName + ".verifyCode", verifyCodeName + ".createTime", verifyCodeName + ".expiredTime");
                return true;
            }
        });

    }

    @Override
    public boolean isValid(VerifyCodeModel verifyCodeEntity) {
        return verifyCodeEntity.getExpireTime() > System.currentTimeMillis();
    }
}
