package com.fansz.auth.service.impl;

import com.fansz.auth.model.SessionInfoResult;
import com.fansz.auth.service.SessionService;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.*;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户session管理
 */
@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    private Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final static String SESSION_PREFIX = "session:";

    private final static String REFRESH_TOKEN_PREFIX = "token:refresh:";

    @Value("${token.access.valid}")
    private int accessValidPeriod;

    @Value("${token.refresh.valid}")
    private int refreshValidPeriod;

    @Autowired
    private JedisTemplate jedisTemplate;

    @Override
    public SessionInfoResult getSession(final String accessToken) {
        SessionInfoResult result = jedisTemplate.execute(new JedisCallback<SessionInfoResult>() {
            @Override
            public SessionInfoResult doInRedis(Jedis jedis) throws Exception {
                Map<String, String> sessionMap = jedis.hgetAll(SESSION_PREFIX + accessToken);
                if (sessionMap != null && !sessionMap.isEmpty()) {
                    SessionInfoResult sessionModel = new SessionInfoResult();
                    BeanTools.copyMapToObject(sessionMap, sessionModel);
                    return sessionModel;
                }
                return null;
            }
        });
        return result;

    }

    @Override
    public Long saveSession(String accessToken, String refreshToken, Long id, String sn) {
        final String sessionKey = SESSION_PREFIX + accessToken;
        final String refreshKey = REFRESH_TOKEN_PREFIX + refreshToken;

        final Map<String, String> session = new HashMap<>();

        session.put("id", String.valueOf(id));
        session.put("sn", sn);
        session.put("accessToken", accessToken);
        session.put("refreshToken", refreshToken);
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));

        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                Date now = DateTools.getSysDate();
                Long aExpire = DateTools.wrapDate(now, "m" + accessValidPeriod).getTime();
                Long rExpire = DateTools.wrapDate(now, "m" + refreshValidPeriod).getTime();
                Pipeline pipe = jedis.pipelined();
                session.put("expiresAt", String.valueOf(aExpire));
                pipe.hmset(sessionKey, session);

                pipe.expire(sessionKey, accessValidPeriod * 60);//默认为5小时,配置文件单位为分钟
                session.put("expiresAt", String.valueOf(rExpire));
                pipe.hmset(refreshKey, session);
                pipe.expire(refreshKey, refreshValidPeriod * 60);//默认为30天,配置文件单位为分钟
                pipe.sync();
                return aExpire;
            }
        });
    }

    @Override
    public void invalidateSession(final String accessToken) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                String refreshToken = jedis.hget(SESSION_PREFIX + accessToken, "refreshToken");
                if (StringTools.isNotBlank(refreshToken)) {
                    jedis.del(SESSION_PREFIX + accessToken);
                    jedis.del(REFRESH_TOKEN_PREFIX + refreshToken);
                }
                return true;
            }
        });
    }

    @Override
    public SessionInfoResult refreshToken(final String refreshToken) {
        return jedisTemplate.execute(new JedisCallback<SessionInfoResult>() {
            @Override
            public SessionInfoResult doInRedis(Jedis jedis) throws Exception {
                final String refreshKey = REFRESH_TOKEN_PREFIX + refreshToken;
                List<String> uidSn = jedis.hmget(refreshKey, "id", "sn", "accessToken");
                if (CollectionTools.isNullOrEmpty(uidSn) || StringTools.isBlank(uidSn.get(0))) {
                    throw new ApplicationException(ErrorCode.TOKEN_INVALID);
                }
                final String accessToken = applyNewToken();
                final String sessionKey = SESSION_PREFIX + accessToken;

                final Map<String, String> session = new HashMap<>();

                session.put("id", uidSn.get(0));
                session.put("sn", uidSn.get(1));
                session.put("accessToken", accessToken);
                session.put("refreshToken", refreshToken);

                Date now = DateTools.getSysDate();
                session.put("lastAccessTime", String.valueOf(now.getTime()));
                Long expire = DateTools.wrapDate(now, "m" + accessValidPeriod).getTime();
                session.put("expiresAt", String.valueOf(expire));
                Pipeline pipe = jedis.pipelined();
                if (uidSn.size() > 2) {
                    pipe.del(SESSION_PREFIX + uidSn.get(2));//删除之前的accessToken
                }
                pipe.hmset(sessionKey, session);
                pipe.hset(refreshKey, "accessToken", accessToken);
                pipe.sync();

                return new SessionInfoResult(accessToken, expire);
            }
        });
    }


    private String applyNewToken() {
        return UUIDTools.generate();
    }
}
