package com.fansz.members.api.provider;

import com.fansz.members.api.VerifyCodeApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.verifycode.VerifyCodeParam;
import com.fansz.members.tools.VerifyCodeType;
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
        verifyCodeService.createVerifyCode(verifyCodeParam.getMobile(), VerifyCodeType.RESET);
        return renderSuccess();
    }

    /**
     * 获取注册验证码接口
     *
     * @param verifyCodeParam 手机号码
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getVerifyCodeForRegister(VerifyCodeParam verifyCodeParam) {
        verifyCodeService.createVerifyCode(verifyCodeParam.getMobile(), VerifyCodeType.REGISTER);
        return renderSuccess();
    }

}
