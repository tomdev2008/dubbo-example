package com.fansz.members.api.service;

import com.fansz.appservice.persistence.domain.User;
import com.fansz.appservice.resource.param.ChangePasswordPara;
import com.fansz.appservice.resource.param.RegisterPara;
import com.fansz.appservice.resource.param.ResetPasswordParam;
import com.fansz.appservice.utils.RegularPattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Created by root on 15-11-3.
 */
@Validated
public interface AccountService {

    public User register(@Valid RegisterPara registerPara);

    /**
     * 获取忘记密码验证码接口
     * @param mobile 手机号码
     */
    public void getPasswordIdentifyCode(
            @Pattern(regexp = RegularPattern.PATTERN_MOBILE, message = "error.mobile.pattern")
            @NotEmpty(message = "error.mobile.empty")
            @Length(max = 20, message = "error.mobile.over")
            String mobile);

    /**
     * 获取注册验证码
     * @param mobile 手机号码
     */
    public void getRegisterIdentifyCode(
            @Pattern(regexp = RegularPattern.PATTERN_MOBILE, message = "error.mobile.pattern")
            @NotEmpty(message = "error.mobile.empty")
            @Length(max = 20, message = "error.mobile.over")
            String mobile);

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
