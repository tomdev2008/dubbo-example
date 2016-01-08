package com.fansz.auth.api;

import com.fansz.auth.model.VerifyCodeParam;
import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;

/**
 * 验证码相关服务
 */
@DubboxService("verifyCodes")
public interface VerifyCodeApi {
    /**
     * 获取验证码,用于重置密码场景
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    @DubboxMethod("getVerifyCodeForReset")
    CommonResult<NullResult> getVerifyCodeForReset(VerifyCodeParam verifyCodeParam) throws ApplicationException;


    /**
     * 获取验证码,用于用户注册场景
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    @DubboxMethod("getVerifyCodeForRegister")
    CommonResult<NullResult> getVerifyCodeForRegister(VerifyCodeParam verifyCodeParam) throws ApplicationException;
}
