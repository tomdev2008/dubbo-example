package com.fansz.auth.service.impl;


import com.fansz.auth.model.VerifyCodeModel;
import com.fansz.auth.model.VerifyCodeType;
import com.fansz.auth.service.AccountService;
import com.fansz.auth.service.SessionService;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.db.entity.UserEntity;
import com.fansz.db.repository.UserDAO;
import com.fansz.fandom.model.account.*;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.SecurityTools;
import com.fansz.pub.utils.UUIDTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员服务逻辑实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private SessionService sessionService;

    private final static String USER_STATUS_OK = "1";

    @Override
    public void register(RegisterParam registerParam) {
        //验证码校验
        VerifyCodeModel verifyCode = verifyCodeService.queryVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        if (verifyCode == null || !verifyCode.getVerifyCode().equals(registerParam.getVerifyCode())) {
            throw new ApplicationException(ErrorCode.VERIFY_ERROR.getCode(), ErrorCode.VERIFY_ERROR.getName());
        }
        if (!verifyCodeService.isValid(verifyCode)) {
            throw new ApplicationException(ErrorCode.VERIFY_ERROR.getCode(), ErrorCode.VERIFY_ERROR.getName());
        }
        //Remove invalid Code
        verifyCodeService.removeVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        UserEntity existedUser = userDAO.findByAccount(registerParam.getLoginname());
        if (existedUser != null) {
            throw new ApplicationException(ErrorCode.USER_EXISTS.getCode(), ErrorCode.USER_EXISTS.getName());
        }
        //Create User
        String encodedPwd = SecurityTools.encode(registerParam.getPassword());
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerParam, user);
        user.setSn(UUIDTools.generate());
        user.setProfileCreatetime(new Date());
        user.setProfileUpdatetime(new Date());
        user.setPassword(encodedPwd);
        user.setMemberStatus(USER_STATUS_OK);
        logger.info("Begin to add profile " + user);

        //Save User Info
        userDAO.save(user);
        logger.info("profile saved:" + user);
    }


    /**
     * 修改密码
     *
     * @param changePasswordParam 修改密码参数
     */
    @Override
    public void changePassword(ChangePasswordParam changePasswordParam) {
        String encodedPwd = SecurityTools.encode(changePasswordParam.getOldPassword());

        //Get User Info
        UserEntity user = userDAO.findBySn(changePasswordParam.getCurrentSn());
        if (user == null) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        if (!user.getPassword().equals(encodedPwd)) {
            throw new ApplicationException(ErrorCode.PASSWORD_WRONG);
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordParam.getNewPassword());
        userDAO.updatePassword(user.getId(), encodedNewPwd);
    }

    /**
     * 重置密码
     *
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) {
        UserEntity user = userDAO.findByMobile(resetPasswordParam.getMobile());
        if (user == null) {//用户不存在
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        //验证码校验
        VerifyCodeModel verifyCode = verifyCodeService.queryVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);

        if (verifyCode == null || !verifyCode.getVerifyCode().equals(resetPasswordParam.getVerifyCode())) {
            throw new ApplicationException(ErrorCode.VERIFY_ERROR);
        }
        if (!verifyCodeService.isValid(verifyCode)) {
            throw new ApplicationException(ErrorCode.VERIFY_ERROR);
        }
        //删除验证码
        verifyCodeService.removeVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);
        String encodedPwd = SecurityTools.encode(resetPasswordParam.getPassword());

        //更新密码
        userDAO.updatePassword(user.getId(), encodedPwd);
    }

    @Override
    public LoginResult login(LoginParam loginParam) {
        UserEntity user = userDAO.findByAccount(loginParam.getLoginname());
        if (user == null) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        String pswdInDb = user.getPassword();
        String encodedPswd = SecurityTools.encode(loginParam.getPassword());
        if (!pswdInDb.equals(encodedPswd)) {
            throw new ApplicationException(ErrorCode.PASSWORD_WRONG);
        }

        String accessKey = UUIDTools.generate();
        String refreshKey = UUIDTools.generate();
        LoginResult result = BeanTools.copyAs(user, LoginResult.class);
        result.setAccessToken(accessKey);
        result.setRefreshToken(refreshKey);
        result.setExpiresAt(-1);

        sessionService.saveSession(accessKey, refreshKey, user.getId(),user.getSn());
        return result;
    }

    @Override
    public void logout(LogoutParam logoutParam) {
        sessionService.invalidateSession(logoutParam.getAccessToken());
    }

}
