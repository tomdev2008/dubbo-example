package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.SessionModel;
import com.fansz.members.api.service.SessionService;
import com.fansz.members.model.session.SessionInfoResult;
import com.fansz.members.tools.BeanTools;
import com.fansz.members.tools.UUIDTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户session管理
 */
@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    private final static String SESSION_PREFIX = "sessions:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SessionInfoResult getSession(String accessToken) {
        String key = SESSION_PREFIX + accessToken;
        Map<Object, Object> sessionMap = redisTemplate.boundHashOps(key).entries();
        if (sessionMap != null) {
            SessionInfoResult sessionModel= BeanTools.copyMapAs(sessionMap,SessionInfoResult.class);
            return sessionModel;
        }
        return null;
    }

    @Override
    public void saveSession(String accessToken, UserEntity user) {
        String key = SESSION_PREFIX + accessToken;
        Map<String, String> session = new HashMap<>();

        session.put("id", String.valueOf(user.getId()));
        session.put("sn", user.getSn());
        session.put("accessToken", UUIDTools.getUniqueId());
        session.put("refreshToken", UUIDTools.getUniqueId());
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));

        //Date expiresAt = DateTools.wrapDate(new Date(), "m+" + MembersConstant.EXPIRED_PERIOD);
        //session.put("expiredAt", String.valueOf(expiresAt.getTime()));
        session.put("expiredAt", "-1");
        redisTemplate.boundHashOps(key).putAll(session);
    }

    @Override
    public void invalidateSession(String accessToken) {
        redisTemplate.delete(SESSION_PREFIX + accessToken);
    }
}
