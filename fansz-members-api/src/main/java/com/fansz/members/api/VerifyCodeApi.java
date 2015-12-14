package com.fansz.members.api;

import javax.ws.rs.*;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.extension.DubboxService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.verifycode.VerifyCodeParam;

/**
 * 验证码相关服务
 */
@Path("/verifyCodes")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
public interface VerifyCodeApi {
    /**
     * 获取验证码,用于重置密码场景
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    @POST
    @Path("/reset")
    @DubboxService("getVerifyCodeForReset")
    CommonResult<NullResult> getVerifyCodeForReset(VerifyCodeParam verifyCodeParam);


    /**
     * 获取验证码,用于用户注册场景
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    @POST
    @Path("/register")
    @DubboxService("getVerifyCodeForRegister")
    CommonResult<NullResult> getVerifyCodeForRegister(VerifyCodeParam verifyCodeParam);
}
