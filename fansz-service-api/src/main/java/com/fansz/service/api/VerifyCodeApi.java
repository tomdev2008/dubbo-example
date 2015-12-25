package com.fansz.service.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.extension.DubboxService;
import com.fansz.service.model.verifycode.VerifyCodeParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
