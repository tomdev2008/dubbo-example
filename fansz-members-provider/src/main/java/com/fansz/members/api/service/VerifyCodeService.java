package com.fansz.members.api.service;

import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.utils.RegularPattern;

import javax.validation.constraints.Pattern;

/**
 * Created by allan on 15/11/26.
 */
public interface VerifyCodeService {
    /**
     * 获取忘记密码验证码接口
     *
     * @param mobile 手机号码
     */
    public boolean getPasswordIdentifyCode(String mobile);

    /**
     * 获取注册验证码
     *
     * @param mobile 手机号码
     */
    public boolean getRegisterIdentifyCode(String mobile);

    VerifyCode getVerifyCode(String mobile, String key);

    void removeVerifyCode(String mobile,String key);

}
