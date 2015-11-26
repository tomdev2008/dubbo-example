package com.fansz.members.api.provider;


import com.fansz.members.api.AccountApi;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.RegisterResult;
import com.fansz.members.model.account.*;
import com.fansz.members.model.param.NullResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户服务
 */
@Component("accountProvider")
public class AccountProvider implements AccountApi {


    @Autowired
    private AccountService accountService;

    /**
     * 注册接口
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    public CommonResult<RegisterResult> register(RegisterParam registerParam) {
        CommonResult<RegisterResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
            UserEntity user = accountService.register(registerParam);
            RegisterResult registerResult = new RegisterResult();
            registerResult.setUid(user.getId());
            result.setResult(registerResult);

        } catch (Exception e) {
            result.setStatus("1");
        }
        return result;
    }

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
            accountService.resetPassword(resetPasswordParam);
        } catch (Exception iae) {
            result.setStatus("1");
        }
        return result;
    }


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");

        try {
            accountService.changePassword(changePasswordParam);

        } catch (Exception iae) {
            result.setStatus("1");
        }
        return result;
    }

    @Override
    public CommonResult<LoginResult> login(LoginParam loginParam) {
        return null;
    }

    @Override
    public CommonResult<NullResult> logout(String uid) {
        return null;
    }
}
