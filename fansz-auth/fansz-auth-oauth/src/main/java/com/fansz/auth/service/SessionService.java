package com.fansz.auth.service;


import com.fansz.auth.model.SessionInfoResult;

/**
 * Created by allan on 15/12/14.
 */
public interface SessionService {

    SessionInfoResult getSession(String accessToken);

    void saveSession(String accessToken, String refreshToken, Long id, String sn);

    void invalidateSession(String accessToken);
}
