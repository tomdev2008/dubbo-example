package com.fansz.auth.provider;

import com.fansz.auth.model.VerifyCodeType;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.api.VerifyCodeApi;
import com.fansz.service.constant.ErrorCode;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.verifycode.VerifyCodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证码服务提供者
 *
 * @author yanyanming
 */
@Component("verifyCodeProvider")
public class VerifyCodeProvider extends AbstractProvider implements VerifyCodeApi {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 获取忘记密码验证码接口
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getVerifyCodeForReset(VerifyCodeParam verifyCodeParam) {
        ErrorCode result = verifyCodeService.createVerifyCode(verifyCodeParam.getMobile(), VerifyCodeType.RESET);
        if (ErrorCode.SUCCESS.equals(result)) {
            return renderSuccess();
        }
        return renderFail(result.getCode(),result.getName());
    }

    /**
     * 获取注册验证码接口
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getVerifyCodeForRegister(VerifyCodeParam verifyCodeParam) {
        ErrorCode result =verifyCodeService.createVerifyCode(verifyCodeParam.getMobile(), VerifyCodeType.REGISTER);
        if (ErrorCode.SUCCESS.equals(result)) {
            return renderSuccess();
        }
        return renderFail(result.getCode(),result.getName());
    }

}
