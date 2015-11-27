package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.account.*;
import com.fansz.members.model.RegisterResult;
import com.fansz.members.model.param.NullResult;

import javax.ws.rs.*;

/**
<<<<<<< HEAD
 * 账户服务
=======
 * 账号相关服务
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
 */
@Path("/accounts")
public interface AccountApi {
    /**
     * 用户注册
     *
<<<<<<< HEAD
     * @param registerParam 注册对象
=======
     * @param registerPara 注册对象
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<RegisterResult> register(RegisterParam registerParam);

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
    public CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam);


<<<<<<< HEAD
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
    public CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam);

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @POST
    @Path("/login")
    public CommonResult<LoginResult> login(LoginParam loginParam);

    /**
     * 用户登出
     *
     * @param uid
     * @return
=======

    /**
     * 修改密码接口
     *
     * @param changePasswordPara 修改密码对象
     * @return resp 返回对象
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
     */
    @POST
    @Path("/logout/{uid}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> logout(@PathParam("uid") String uid);
}
