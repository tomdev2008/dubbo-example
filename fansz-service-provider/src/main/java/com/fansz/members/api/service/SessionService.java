package com.fansz.members.api.service;

import com.fansz.members.model.session.SessionInfoResult;

/**
 * Created by allan on 15/12/14.
 */
public interface SessionService {

    SessionInfoResult getSession(String accessToken);

    void saveSession(String accessToken, String refreshToken, Long uid);

    void invalidateSession(String accessToken);
}
