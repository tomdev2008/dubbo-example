package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.extension.DubboxService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.account.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 账户服务
 */
@Path("/accounts")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface AccountApi {
    /**
     * 用户注册
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @DubboxService("register")
    CommonResult<NullResult> register(RegisterParam registerParam);

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @POST
    @Path("/login")
    @DubboxService("login")
    CommonResult<LoginResult> login(LoginParam loginParam);

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/reset")
    @DubboxService("resetPassword")
    CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam);


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/change")
    @DubboxService("modifyPassword")
    CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam);

    /**
     * 用户登出
     *
     * @param logoutParam
     * @param logoutParam 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/logout")
    @DubboxService("logout")
    CommonResult<NullResult> logout(LogoutParam logoutParam);

    @DubboxService("refreshToken")
    CommonResult<LoginResult> refreshToken(String refreshToken);
}
