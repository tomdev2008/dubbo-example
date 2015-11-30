package com.fansz.members.api.service;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.SessionModel;
import com.fansz.members.model.account.*;

/**
 * Created by root on 15-11-3.
 */
public interface AccountService {

    UserEntity register(RegisterParam registerParam);


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
    void resetPassword(com.fansz.members.model.account.ResetPasswordParam resetPasswordParam);

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

    /**
     * 根据accessToken返回session信息
     * @param accessToken
     * @return
     */
    SessionModel getSession(String accessToken);
}
