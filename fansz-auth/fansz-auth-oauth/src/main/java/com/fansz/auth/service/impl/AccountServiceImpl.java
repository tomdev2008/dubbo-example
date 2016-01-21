package com.fansz.auth.service.impl;


import com.fansz.auth.model.*;
import com.fansz.auth.service.AccountService;
import com.fansz.auth.service.SessionService;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.*;
import com.fansz.redis.UserTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 会员服务逻辑实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private SessionService sessionService;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    private final static String USER_STATUS_OK = "1";

    @Autowired
    private UserDAO userDAO;

    /**
     * 用户注册
     *
     * @param registerParam
     */
    @Override
    public void register(RegisterParam registerParam) throws ApplicationException {
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

        String sn = userTemplate.getSnByAccount(registerParam.getLoginname());
        if (StringTools.isNotBlank(sn)) {
            throw new ApplicationException(ErrorCode.USER_EXISTS.getCode(), ErrorCode.USER_EXISTS.getName());
        }
        Date now = DateTools.getSysDate();
        //Create User
        String encodedPwd = SecurityTools.encode(registerParam.getPassword());
        User user = new User();
        BeanTools.copy(registerParam, user);
        user.setSn(UUIDTools.generate());
        user.setProfileCreatetime(now);
        user.setProfileUpdatetime(now);
        user.setPassword(encodedPwd);
        user.setMemberStatus(USER_STATUS_OK);
        //注册时,默认昵称为手机号
        user.setNickname(user.getMobile());
        userDAO.save(user);

        Map<String, Object> userMap = JsonHelper.convertJSONString2Object(JsonHelper.convertObject2JSONString(user), Map.class);
        /**
         Map<String, Object> userMap = new HashMap<>();
         userMap.put("id", userTemplate.generateUserId());
         userMap.put("sn", UUIDTools.generate());
         userMap.put("loginname", registerParam.getLoginname());
         userMap.put("password", registerParam.getPassword());
         userMap.put("mobile", registerParam.getMobile());
         userMap.put("member_status", USER_STATUS_OK);
         userMap.put("profile_createtime", now);
         userMap.put("profile_updatetime", now);
         userMap.put("password", encodedPwd);
         *
         */
        //将用户信息保存到redis
        userTemplate.addUser(userMap);
    }


    /**
     * 修改密码
     *
     * @param changePasswordParam 修改密码参数
     */
    @Override
    public void changePassword(ChangePasswordParam changePasswordParam) throws ApplicationException {
        String encodedPwd = SecurityTools.encode(changePasswordParam.getOldPassword());

        //Get User Info
        Map<String, String> userMap = userTemplate.get(changePasswordParam.getCurrentSn());
        if (userMap == null || userMap.isEmpty()) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        if (!userMap.get("password").equals(encodedPwd)) {
            throw new ApplicationException(ErrorCode.PASSWORD_WRONG);
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordParam.getNewPassword());
        userTemplate.updatePasswd(changePasswordParam.getCurrentSn(), encodedNewPwd);//更新redis中的密码
    }

    /**
     * 重置密码
     *
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) throws ApplicationException {
        String userSn = userTemplate.getSnByMobile(resetPasswordParam.getMobile());
        if (StringTools.isBlank(userSn)) {//用户不存在
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
        userTemplate.updatePasswd(userSn, encodedPwd);//更新redis中的密码
    }

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     */
    @Override
    public LoginResult login(LoginParam loginParam) throws ApplicationException {
        String userSn = userTemplate.getSnByAccount(loginParam.getLoginname());
        if (StringTools.isBlank(userSn)) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        Map<String, String> userMap = userTemplate.getWithAuthInfo(userSn);
        String pswdInDb = userMap.get("password");
        String encodedPswd = SecurityTools.encode(loginParam.getPassword());
        if (!pswdInDb.equals(encodedPswd)) {
            throw new ApplicationException(ErrorCode.PASSWORD_WRONG);
        }

        String accessKey = UUIDTools.generate();
        String refreshKey = UUIDTools.generate();
        Long expireAt = sessionService.saveSession(accessKey, refreshKey, Long.valueOf(userMap.get("id")), userSn);

        LoginResult result = JsonHelper.copyMapAs(userMap, LoginResult.class);
        result.setAccessToken(accessKey);
        result.setRefreshToken(refreshKey);
        result.setExpiresAt(expireAt);

        //请求IM服务器 获取IM token


        return result;
    }

    /**
     * 用户退出登录
     *
     * @param logoutParam
     */
    @Override
    public void logout(LogoutParam logoutParam) throws ApplicationException {
        sessionService.invalidateSession(logoutParam.getAccessToken());
    }


}
