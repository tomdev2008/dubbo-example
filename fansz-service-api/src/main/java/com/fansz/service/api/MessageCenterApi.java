package com.fansz.service.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.service.extension.DubboxService;
import com.fansz.service.model.messagecenter.QueryMessageParam;
import com.fansz.service.model.messagecenter.MessageCenterResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dell on 2015/12/11.
 */
@Path("/message")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface MessageCenterApi {

    /**
     * 会员获取消息(支持分页)
     *
     * @param queryMessageParam
     * @return
     */
    @POST
    @Path("/get")
    @DubboxService("myRemindCenter")
    CommonPagedResult<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
