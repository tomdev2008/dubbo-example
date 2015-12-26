package com.fansz.auth.service;


import com.fansz.fandom.model.account.*;

/**
 * Created by root on 15-11-3.
 */
public interface AccountService {

    void register(RegisterParam registerParam);


    /**
     * 修改密码
     *
     * @param changePasswordParam 修改密码对象
     */
    void changePassword(ChangePasswordParam changePasswordParam);

    /**
     * 重置密码
     *
     * @param resetPasswordParam 重置密码对象
     */
    void resetPassword(ResetPasswordParam resetPasswordParam);

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    LoginResult login(LoginParam loginParam);

    /**
     * 退出登陆
     *
     * @param logoutParam
     */
    void logout(LogoutParam logoutParam);
}
