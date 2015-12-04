package com.fansz.members.api.provider;


import com.fansz.members.api.AccountApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.tools.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.account.*;
import com.fansz.members.model.NullResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户服务
 */
@Component("accountProvider")
public class AccountProvider extends AbstractProvider implements AccountApi {


    @Autowired
    private AccountService accountService;

    /**
     * 注册接口
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> register(RegisterParam registerParam) {
        accountService.register(registerParam);
        return renderSuccess(PRESENCE, "Register successfully");
    }

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam) {
        accountService.resetPassword(resetPasswordParam);
        return renderSuccess(PRESENCE, "Reset password successfully");
    }


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam) {
        accountService.changePassword(changePasswordParam);
        return renderSuccess(PRESENCE, "Change password successfully");
    }

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @Override
    public CommonResult<LoginResult> login(LoginParam loginParam) {
        LoginResult loginResult = accountService.login(loginParam);
        return renderSuccess(loginResult, "Login successfully");
    }

    /**
     * 用户退出登陆
     *
     * @param logoutParam 修改密码对象
     * @return
     */
    @Override
    public CommonResult<NullResult> logout(LogoutParam logoutParam) {
        accountService.logout(logoutParam);
        return renderSuccess(PRESENCE, "Logout successfully");
    }
}
