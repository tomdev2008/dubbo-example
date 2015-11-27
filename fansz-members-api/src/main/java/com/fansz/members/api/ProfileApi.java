package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.user.UserInfoResult;
import com.fansz.members.model.user.ModifyProfileParam;
import com.fansz.members.model.user.QueryUserParam;

import javax.ws.rs.*;

/**
 * 用户服务
 */
@Path("/users")
public interface ProfileApi {
    /**
     * 获得当前登陆用户的详细信息
     *
     * @param queryUserParam
     * @return
     */
    @Path("/show")
    @GET
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<UserInfoResult> getProfile(QueryUserParam queryUserParam);

    /**
     * 修改当前用户的配置
     *
     * @param modifyProfileParam
     * @return
     */
    @POST
    @Path("/modify")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam);

}
