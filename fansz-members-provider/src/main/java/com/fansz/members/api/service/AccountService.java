package com.fansz.members.api.service;

import com.fansz.members.model.User;
import com.fansz.members.model.param.ChangePasswordPara;
import com.fansz.members.model.param.RegisterPara;
import com.fansz.members.model.param.ResetPasswordParam;

import javax.validation.Valid;

/**
 * Created by root on 15-11-3.
 */
public interface AccountService {

    public User register(@Valid RegisterPara registerPara);

    /**
     * 获取忘记密码验证码接口
     * @param mobile 手机号码
     */
    public void getPasswordIdentifyCode(String mobile);

    /**
     * 获取注册验证码
     * @param mobile 手机号码
     */
    public void getRegisterIdentifyCode(String mobile);

    /**
     * 修改密码
     * @param changePasswordPara 修改密码对象
     */
    void changePassword(@Valid ChangePasswordPara changePasswordPara);

    /**
     * 重置密码
     * @param resetPasswordParam 重置密码对象
     */
    void resetPassword(@Valid ResetPasswordParam resetPasswordParam);
}
