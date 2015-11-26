package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.param.NullResult;
import com.fansz.members.model.param.UserInfoResult;
import com.fansz.members.model.user.ModifyProfileParam;
import com.fansz.members.model.user.QueryUserParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * 用户服务
 */
@Path("/users")
public interface UserApi {

    @Path("/show")
    @GET
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<UserInfoResult> getProfile(QueryUserParam queryUserParam);

    @POST
    @Path("/modify")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam);

    /**
     * 获取我所有关注的fandom接口
     *
     * @param form 个人头像信息
     * @return resp 返回对象
     */
    @POST
    @Path("/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> setAvatar(InputStream form);


}
