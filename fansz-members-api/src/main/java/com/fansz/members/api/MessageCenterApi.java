package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.message.QueryMessageParam;
import com.fansz.members.model.messagecenter.MessageCenterResult;

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
    CommonPagedResult<MessageCenterResult> getMessageByMemberSn(QueryMessageParam queryMessageParam);

}
