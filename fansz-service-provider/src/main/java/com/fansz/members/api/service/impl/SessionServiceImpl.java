package com.fansz.members.api.service.impl;

import com.fansz.members.api.service.SessionService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.session.SessionInfoResult;
import com.fansz.members.redis.JedisTemplate;
import com.fansz.members.redis.support.JedisCallback;
import com.fansz.members.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.StringTools;
import com.fansz.pub.utils.UUIDTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户session管理
 */
@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    private Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final static String ACCESS_TOKEN_PREFIX = "token:access:";

    private final static String REFRESH_TOKEN_PREFIX = "token:refresh:";

    @Value("token.access.valid")
    private int accessValidPeriod;

    @Value("token.refresh.valid")
    private int refreshValidPeriod;

    @Autowired
    private JedisTemplate jedisTemplate;

    @Override
    public SessionInfoResult getSession(final String accessToken) {

        SessionInfoResult result = jedisTemplate.execute(new JedisCallback<SessionInfoResult>() {
            @Override
            public SessionInfoResult doInRedis(Jedis jedis) throws Exception {
                Map<String, String> sessionMap = jedis.hgetAll(ACCESS_TOKEN_PREFIX + accessToken);
                if (sessionMap != null) {
                    SessionInfoResult sessionModel = new SessionInfoResult();
                    BeanTools.copyMapToObject(sessionMap, SessionInfoResult.class);
                    return sessionModel;
                }
                return null;
            }
        });
        return result;

    }

    @Override
    public void saveSession(String accessToken, String refreshToken, Long uid) {
        final String accessKey = ACCESS_TOKEN_PREFIX + accessToken;
        final String refreshKey = ACCESS_TOKEN_PREFIX + refreshToken;

        final Map<String, String> session = new HashMap<>();

        session.put("id", String.valueOf(uid));
        session.put("accessToken", accessToken);
        session.put("refreshToken", refreshToken);
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));

        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Pipeline pipe = jedis.pipelined();
                pipe.hmset(accessKey, session);
                pipe.expire(accessKey, accessValidPeriod * 3600);//默认为5小时
                session.remove("accessToken");
                pipe.hmset(refreshKey, session);
                pipe.expire(refreshKey, accessValidPeriod * 3600);
                pipe.sync();
                return true;
            }
        });
    }

    @Override
    public void invalidateSession(final String accessToken) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.del(ACCESS_TOKEN_PREFIX + accessToken);
                return true;
            }
        });
    }

    public String refreshToken(final String refreshToken) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                final String refreshKey = ACCESS_TOKEN_PREFIX + refreshToken;
                String uid = jedis.hget(refreshKey, "id");
                if (!StringTools.isBlank(uid)) {
                    throw new ApplicationException(Constants.TOKEN_INVALID, "Token invalid");
                }
                final String accessToken = applyNewToken();
                final String accessKey = ACCESS_TOKEN_PREFIX + accessToken;

                final Map<String, String> session = new HashMap<>();

                session.put("id", String.valueOf(uid));
                session.put("accessToken", accessToken);
                session.put("refreshToken", refreshToken);
                session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
                jedis.hmset(accessKey, session);
                return accessToken;
            }
        });
    }


    private String applyNewToken() {
        return UUIDTools.generate();
    }
}
