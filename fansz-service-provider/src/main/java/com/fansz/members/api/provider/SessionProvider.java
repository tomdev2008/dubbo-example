package com.fansz.members.api.provider;

import com.fansz.members.api.SessionApi;
import com.fansz.members.api.service.SessionService;
import com.fansz.members.model.session.SessionInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 15/12/15.
 */
@Component("sessionProvider")
public class SessionProvider implements SessionApi {
    @Autowired
    private SessionService sessionService;

    @Override
    public SessionInfoResult getSession(String accessToken) {
        return sessionService.getSession(accessToken);
    }
}
