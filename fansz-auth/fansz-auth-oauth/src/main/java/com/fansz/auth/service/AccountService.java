package com.fansz.auth.service;


import com.fansz.auth.model.*;
import com.fansz.common.provider.exception.ApplicationException;

import java.util.Map;

/**
 * Created by root on 15-11-3.
 */
public interface AccountService {

    void register(RegisterParam registerParam) throws ApplicationException;


    /**
     * 修改密码
     *
     * @param changePasswordParam 修改密码对象
     */
    void changePassword(ChangePasswordParam changePasswordParam) throws ApplicationException;

    /**
     * 重置密码
     *
     * @param resetPasswordParam 重置密码对象
     */
    void resetPassword(ResetPasswordParam resetPasswordParam) throws ApplicationException;

    /**
     * 用户登陆
     *
     * @param loginParam
     * @return
     */
    LoginResult login(LoginParam loginParam) throws ApplicationException;

    /**
     * 退出登陆
     *
     * @param logoutParam
     */
    void logout(LogoutParam logoutParam) throws ApplicationException;

    Map<String, String> requestIMToken(String appKey, String memberSn) throws ApplicationException;
}
