package com.fansz.members.api;

import com.fansz.members.model.session.SessionInfoResult;

import javax.ws.rs.Path;


@Path("sessions")
public interface SessionApi {
    SessionInfoResult getSession(String accessToken);
}
