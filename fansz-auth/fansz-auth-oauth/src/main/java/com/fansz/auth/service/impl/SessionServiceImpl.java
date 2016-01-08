package com.fansz.auth.service.impl;

import com.fansz.auth.model.SessionInfoResult;
import com.fansz.auth.service.SessionService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.UUIDTools;
import com.fansz.redis.UserTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户session管理
 */
@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    private Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);


    @Value("${token.access.valid}")
    private int accessValidPeriod;

    @Value("${token.refresh.valid}")
    private int refreshValidPeriod;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Override
    public SessionInfoResult getSession(final String accessToken) {
        Map<String, String> sessionMap = userTemplate.getSession(accessToken);
        if (sessionMap != null && !sessionMap.isEmpty()) {
            SessionInfoResult sessionModel = new SessionInfoResult();
            BeanTools.copyMapToObject(sessionMap, sessionModel);
            return sessionModel;
        }
        return null;

    }

    @Override
    public Long saveSession(String accessToken, String refreshToken, Long id, String sn) {
        final Map<String, String> session = new HashMap<>();
        session.put("id", String.valueOf(id));
        session.put("sn", sn);
        session.put("accessToken", accessToken);
        session.put("refreshToken", refreshToken);
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
        return userTemplate.saveSession(accessToken, refreshToken, session, accessValidPeriod, refreshValidPeriod);
    }

    @Override
    public void invalidateSession(final String accessToken) throws ApplicationException {
        userTemplate.invalidateSession(accessToken);
    }

    @Override
    public SessionInfoResult refreshToken(final String refreshToken) {
        final String accessToken = applyNewToken();
        Long expire = userTemplate.refreshToken(refreshToken, accessToken, accessValidPeriod);
        return new SessionInfoResult(accessToken, expire);
    }


    private String applyNewToken() {
        return UUIDTools.generate();
    }
}
