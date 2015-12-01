package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.account.*;
import com.fansz.members.model.NullResult;

import javax.ws.rs.*;

/**
 * 账户服务
 */
@Path("/accounts")
public interface AccountApi {
    /**
     * 用户注册
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> register(RegisterParam registerParam);

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/reset")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam);


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/change")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam);

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @POST
    @Path("/login")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<LoginResult> login(LoginParam loginParam);

    /**
     * 用户登出
     *
     * @param logoutParam
     * @param logoutParam 修改密码对象
     * @return resp 返回对象
     */
    @GET
    @Path("/logout")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> logout(LogoutParam logoutParam);
}
