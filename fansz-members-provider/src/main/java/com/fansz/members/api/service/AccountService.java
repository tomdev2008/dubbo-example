package com.fansz.members.api.service;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.account.ChangePasswordParam;
import com.fansz.members.model.account.RegisterParam;
import com.fansz.members.model.account.ResetPasswordParam;
/**
 * Created by root on 15-11-3.
 */
public interface AccountService {

    public UserEntity register(RegisterParam registerParam);


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
}
