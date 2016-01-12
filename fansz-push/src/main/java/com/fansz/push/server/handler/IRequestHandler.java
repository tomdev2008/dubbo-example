package com.fansz.push.server.handler;

import com.fansz.constant.RequestType;
import com.fansz.push.server.model.JsonRequest;
import com.fansz.push.server.session.ClientSession;

public interface IRequestHandler {

    RequestType getListenerName();

    void onEvent(JsonRequest request, ClientSession conn);
}
