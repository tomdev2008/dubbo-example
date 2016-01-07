package com.fansz.auth.api;

import com.fansz.auth.model.*;
import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;

/**
 * 账户服务
 */
@DubboxService("accounts")
public interface AccountApi {
    /**
     * 用户注册
     *
     * @param registerParam 注册对象
     * @return resp 返回对象
     */
    @DubboxMethod("register")
    CommonResult<NullResult> register(RegisterParam registerParam) throws ApplicationException;

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    @DubboxMethod("login")
    CommonResult<LoginResult> login(LoginParam loginParam) throws ApplicationException;

    /**
     * 重置密码接口
     *
     * @param resetPasswordParam 密码对象
     * @return resp 返回对象
     */
    @DubboxMethod("resetPassword")
    CommonResult<NullResult> resetPassword(ResetPasswordParam resetPasswordParam) throws ApplicationException;


    /**
     * 修改密码接口
     *
     * @param changePasswordParam 修改密码对象
     * @return resp 返回对象
     */
    @DubboxMethod("modifyPassword")
    CommonResult<NullResult> changePassword(ChangePasswordParam changePasswordParam) throws ApplicationException;

    /**
     * 用户登出
     *
     * @param logoutParam
     * @param logoutParam 修改密码对象
     * @return resp 返回对象
     */
    @DubboxMethod("logout")
    CommonResult<NullResult> logout(LogoutParam logoutParam) throws ApplicationException;

    @DubboxMethod("refreshToken")
    CommonResult<LoginResult> refreshToken(RefreshTokenParam refreshTokenParam) throws ApplicationException;

    @DubboxMethod("getSession")
    SessionInfoResult getSession(SessionQueryParam sessionQueryParam) throws ApplicationException;
}
