package com.fansz.members.api;

import com.fansz.members.model.param.ChangePasswordPara;
import com.fansz.members.model.param.RegisterPara;
import com.fansz.members.model.param.ResetPasswordParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/account")
public interface Account {

    /**
     * 重置密码接口
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/reset")
    @Consumes("application/json")
    @Produces("application/json")
    public Response resetPassword(ResetPasswordParam resetPasswordParam);

    /**
     * 获取忘记密码验证码接口
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/identify/password/{mobile}")
    @Produces("application/json")
    public Response getPasswordIdentifyCode(@PathParam("mobile") String mobile);

    /**
     * 获取注册验证码接口
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/identify/register/{mobile}")
    @Produces("application/json")
    public Response getRegisterIdentifyCode(@PathParam("mobile") String mobile);

    /**
     * 修改密码接口
     * @param changePasswordPara 修改密码对象
     * @return resp 返回对象
     */
    @POST
    @Path("/password/change")
    @Consumes("application/json")
    @Produces("application/json")
    public Response changePassword(ChangePasswordPara changePasswordPara);

    /**
     * 注册接口
     * @param registerPara 注册对象
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(RegisterPara registerPara);
}
