package com.fansz.members.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * 验证码相关服务
 */
@Path("/verifyCodes")
public interface VerifyCodeApi {
    /**
     * 获取验证码,用于忘记密码场景
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/passwd/{mobile}")
    @Produces("application/json")
    public Response getPasswordIdentifyCode(@PathParam("mobile") String mobile);

    /**
     * 获取验证码,用于用户注册场景
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/register/{mobile}")
    @Produces("application/json")
    public Response getRegisterIdentifyCode(@PathParam("mobile") String mobile);
}
