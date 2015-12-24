package com.fansz.auth.service;

import com.fansz.service.model.session.SessionInfoResult;

/**
 * Created by allan on 15/12/14.
 */
public interface SessionService {

    SessionInfoResult getSession(String accessToken);

    void saveSession(String accessToken, String refreshToken, Long uid);

    void invalidateSession(String accessToken);
}
