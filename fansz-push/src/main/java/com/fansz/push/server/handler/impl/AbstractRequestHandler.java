package com.fansz.push.server.handler.impl;

import com.fansz.constant.AppConstants;
import com.fansz.push.server.handler.IRequestHandler;
import com.fansz.push.server.model.JsonRequest;
import com.fansz.push.server.model.ResponseHeader;
import com.fansz.push.server.model.JsonResponse;
import com.fansz.push.server.session.ClientSession;

public abstract class AbstractRequestHandler implements IRequestHandler {
    /**
     * 返回处理成功消息
     * 
     * @param request
     * @param session
     * @return
     */
    protected void renderSuccess(JsonRequest request, ClientSession session, String body) {
        JsonResponse response = new JsonResponse();
        ResponseHeader header = new ResponseHeader();
        header.setReqId(request.getHeader().getReqId());
        header.setStatus(AppConstants.CODE_SUCCESS);
        header.setMessage("处理成功");
        header.setResType(request.getHeader().getReqType());
        response.setHeader(header);
        response.setBody(body);
        session.deliver(response);
    }

    /**
     * 返回失败消息
     * 
     * @param request
     * @param session
     * @param errorMsg
     */
    protected void renderFail(JsonRequest request, ClientSession session, String errorMsg) {
        JsonResponse response = new JsonResponse();
        ResponseHeader header = new ResponseHeader();
        header.setReqId(request.getHeader().getReqId());
        header.setResType(request.getHeader().getReqType());
        header.setStatus(AppConstants.CODE_FAIL);
        header.setMessage(errorMsg);
        response.setHeader(header);
        session.deliver(response);
    }
}
