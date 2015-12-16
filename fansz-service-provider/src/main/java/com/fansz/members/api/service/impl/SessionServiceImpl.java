package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.SessionModel;
import com.fansz.members.api.service.SessionService;
import com.fansz.members.model.session.SessionInfoResult;
import com.fansz.members.redis.RedisSessionFactory;
import com.fansz.members.tools.BeanTools;
import com.fansz.members.tools.UUIDTools;
import com.sun.deploy.association.utility.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private RedisSessionFactory redisSessionFactory;

    @Override
    public SessionInfoResult getSession(String accessToken) {
        SessionInfoResult result = null;

        Jedis jedis = redisSessionFactory.getRedisClient();
        if (jedis == null) {
            return null;
        }

        try {
            Map<String, String> sessionMap = jedis.hgetAll(ACCESS_TOKEN_PREFIX + accessToken);
            if (sessionMap != null) {
                SessionInfoResult sessionModel = BeanTools.copyMapAs(sessionMap, SessionInfoResult.class);
                return sessionModel;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            redisSessionFactory.returnResource(jedis);
        }
        return result;
    }

    @Override
    public void saveSession(String accessToken, String refreshToken, Long uid) {
        String accessKey = ACCESS_TOKEN_PREFIX + accessToken;
        String refreshKey = ACCESS_TOKEN_PREFIX + refreshToken;
        Map<String, String> session = new HashMap<>();

        session.put("id", String.valueOf(uid));
        session.put("accessToken", accessToken);
        session.put("refreshToken", refreshToken);
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));

        Jedis jedis = redisSessionFactory.getRedisClient();
        if (jedis == null) {
            return;
        }

        try {
            Pipeline pipe = jedis.pipelined();
            pipe.hmset(accessKey, session);
            pipe.expire(accessKey, accessValidPeriod * 3600);//默认为5小时
            session.remove("accessToken");
            pipe.hmset(refreshKey, session);
            pipe.expire(refreshKey, accessValidPeriod * 3600);
            pipe.sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            redisSessionFactory.returnResource(jedis);
        }


    }

    @Override
    public void invalidateSession(String accessToken) {
        Jedis jedis = redisSessionFactory.getRedisClient();
        if (jedis == null) {
            return;
        }

        try {
            jedis.del(ACCESS_TOKEN_PREFIX + accessToken);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            redisSessionFactory.returnResource(jedis);
        }
    }
}
