package com.fansz.members.api.provider;

import com.fansz.members.api.VerifyCodeApi;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.param.NullResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Vector;

/**
 * Created by allan on 15/11/26.
 */
public class VerifyCodeProvider implements VerifyCodeApi {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 获取忘记密码验证码接口
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getVerifyCodeForReset(@PathParam("mobile") String mobile) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
            verifyCodeService.getPasswordIdentifyCode(mobile);
        } catch (Exception iae) {
            result.setStatus("1");
        }
        return result;

    }

    /**
     * 获取注册验证码接口
     *
     * @param mobile 手机号码
     * @return resp 返回对象
     */
    public CommonResult<NullResult> getVerifyCodeForRegister(@PathParam("mobile") String mobile) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
            verifyCodeService.getRegisterIdentifyCode(mobile);
        } catch (Exception iae) {
            result.setStatus("1");
        }
        return result;
    }

}
