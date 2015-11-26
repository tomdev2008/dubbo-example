package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.param.NullResult;

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
     * 获取验证码,用于重置密码场景
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/reset/{mobile}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> getVerifyCodeForReset(@PathParam("mobile") String mobile);

    /**
     * 获取验证码,用于用户注册场景
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    @GET
    @Path("/register/{mobile}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<NullResult> getVerifyCodeForRegister(@PathParam("mobile") String mobile);
}
