package com.fansz.auth.provider;


import com.fansz.auth.service.AccountService;
import com.fansz.auth.service.SessionService;
import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.AccountApi;
import com.fansz.fandom.model.account.*;
import com.fansz.fandom.model.session.SessionInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户服务
 */
@Component("accountProvider")
public class AccountProvider extends AbstractProvider implements AccountApi {


    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    /**
     * 注册接口
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> register(RegisterParam registerParam) {
        accountService.register(registerParam);
        return renderSuccess();
    }

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam) {
        accountService.resetPassword(resetPasswordParam);
        return renderSuccess();
    }


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    public CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam) {
        accountService.changePassword(changePasswordParam);
        return renderSuccess();
    }

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @Override
    public CommonResult<LoginResult> login(LoginParam loginParam) throws ApplicationException {
        LoginResult loginResult = accountService.login(loginParam);
        return renderSuccess(loginResult);
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
        return renderSuccess();
    }

    @Override
    public CommonResult<LoginResult> refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public SessionInfoResult getSession(String accessToken) {
        return sessionService.getSession(accessToken);
    }
}
