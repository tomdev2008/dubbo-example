package com.fansz.push.server.event.handler;

import com.fansz.constant.AppConstants;
import com.fansz.push.server.event.BusinessEvent;
import com.fansz.push.server.event.EventType;
import com.fansz.push.server.event.IEventHandler;
import com.fansz.push.server.model.RegisterParam;
import com.fansz.push.server.model.JsonRequest;
import com.fansz.push.server.model.JsonResponse;
import com.fansz.push.server.session.ClientSession;
import com.fansz.push.server.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fansz.push.server.model.ResponseHeader;

import com.alibaba.fastjson.JSON;

@Component("loginEventHandler")
public class LoginEventHandler implements IEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginEventHandler.class);

    @Override
    public void handle(BusinessEvent event) {
        JsonRequest request = (JsonRequest) event.getObj();
        ClientSession session = event.getSession();
        String body = request.getBody();
        RegisterParam login = JSON.parseObject(body, RegisterParam.class);
        login.setAppKey(request.getHeader().getAppKey());
        login.setSn(request.getHeader().getSn());
        // 如果传入参数错误，返回错误消息
        boolean result = true;
        // 参数校验通过
        try {
            sessionManager.register(session, login);
            result = true;
        } catch (Exception e) {
            logger.error("创建用户session时出错", e);
        }
        if (result) {
            session.deliver(onSessionCreatedSuccess(request, login), true);
        } else {
            session.deliver(onParameterError(request));
        }

    }


    /**
     * 用户登陆成功
     *
     * @param request
     * @param login
     * @return
     */
    private JsonResponse onSessionCreatedSuccess(JsonRequest request, RegisterParam login) {
        JsonResponse response = new JsonResponse();
        ResponseHeader header = new ResponseHeader();
        header.setReqId(request.getHeader().getReqId());
        header.setStatus(AppConstants.CODE_SUCCESS);
        header.setResType(request.getHeader().getReqType());
        response.setHeader(header);
        response.setBody(String.format("{\"uid\":\"%s\"}", login.getSn()));
        return response;
    }

    /**
     * 用户登陆失败
     *
     * @param request
     * @return
     */
    private JsonResponse onSessionCreatedFail(JsonRequest request) {
        JsonResponse response = new JsonResponse();
        ResponseHeader header = new ResponseHeader();
        header.setReqId(request.getHeader().getReqId());
        header.setResType(request.getHeader().getReqType());
        header.setStatus(AppConstants.CODE_REGISTED_ERROR_UID);
        header.setMessage("UID错误");
        response.setHeader(header);
        return response;
    }

    /**
     * 登陆传入参数错误
     *
     * @param request
     * @return
     */
    private JsonResponse onParameterError(JsonRequest request) {
        JsonResponse response = new JsonResponse();
        ResponseHeader header = new ResponseHeader();
        header.setReqId(request.getHeader().getReqId());
        header.setResType(request.getHeader().getReqType());
        header.setStatus(AppConstants.CODE_FAIL);
        header.setMessage("未传入必须参数!");
        response.setHeader(header);
        return response;
    }

    @Override
    public EventType getEventType() {
        return EventType.LOGIN;
    }

    private SessionManager sessionManager;
}
