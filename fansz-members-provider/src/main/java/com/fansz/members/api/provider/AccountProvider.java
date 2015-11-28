package com.fansz.members.api.provider;


import com.fansz.members.api.AccountApi;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.RegisterResult;
import com.fansz.members.model.account.*;
import com.fansz.members.model.NullResult;
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
        result.setStatus(Constants.SUCCESS);
        RegisterResult registerResult = accountService.register(registerParam);
        result.setResult(registerResult);
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
        result.setStatus(Constants.SUCCESS);
        accountService.resetPassword(resetPasswordParam);
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
        result.setStatus(Constants.SUCCESS);
        accountService.changePassword(changePasswordParam);
        return result;
    }

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @Override
    public CommonResult<LoginResult> login(LoginParam loginParam) {
        CommonResult<LoginResult> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        LoginResult loginResult = accountService.login(loginParam);
        result.setResult(loginResult);
        return result;
    }

    /**
     * 用户退出登陆
     *
     * @param uid 修改密码对象
     * @return
     */
    @Override
    public CommonResult<NullResult> logout(String uid) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        accountService.logout(uid);
        return result;
    }
}
