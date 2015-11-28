package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.api.utils.VerifyCodeType;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.RegisterResult;
import com.fansz.members.model.account.*;
import com.fansz.members.tools.DateTools;
import com.fansz.members.tools.MembersConstant;
import com.fansz.members.tools.SecurityTools;
import com.fansz.members.tools.UUIDTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-11-3.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public RegisterResult register(RegisterParam registerParam) {
        //验证码校验
        VerifyCode verifyCode = verifyCodeService.queryVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        if (verifyCode == null || verifyCode.getVerifyCode().equals(registerParam.getVerifyCode())) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "验证码错误");
        }
        //Remove invalid Code
        verifyCodeService.removeVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        //Create User
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerParam, user);
        user.setSn(UUIDTools.getUniqueId());
        logger.info("Begin to add profile " + user);

        //Save User Info
        userEntityMapper.insertSelective(user);
        logger.info("profile saved:" + user);
        RegisterResult registerResult = new RegisterResult();
        registerResult.setUid(user.getSn());
        putSessionInRedis(registerResult);
        return registerResult;
    }

    private void putSessionInRedis(RegisterResult registerResult) {
        String key = "session:" + registerResult.getUid();
        Map<String, String> session = new HashMap<>();
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
        session.put("accessToken", UUIDTools.getUniqueId());
        session.put("refreshToken", UUIDTools.getUniqueId());
        Date expiresAt = DateTools.wrapDate(new Date(), "m+" + MembersConstant.EXPIRED_PERIOD);
        session.put("expiredAt", String.valueOf(expiresAt.getTime()));
        redisTemplate.boundHashOps(key).putAll(session);
    }

    /**
     * 修改密码
     *
     * @param changePasswordPara 修改密码参数
     */
    @Override
    public void changePassword(ChangePasswordParam changePasswordPara) {
        String encodedPwd = SecurityTools.encode(changePasswordPara.getOldPassword());

        //Get User Info
        UserEntity user = userEntityMapper.selectByPrimaryKey(changePasswordPara.getUid());
        if (user == null || !user.getPassword().equals(encodedPwd)) {
            throw new ApplicationException(Constants.USER_NOT_FOUND, "用户不存在");
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordPara.getNewPassword());
        userEntityMapper.updatePassword(changePasswordPara.getUid(), encodedNewPwd);
    }

    /**
     * 重置密码
     *
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) {
        UserEntity userEntity = userEntityMapper.findByMoblie(resetPasswordParam.getMobile());
        if (userEntity == null) {//用户不存在
            throw new ApplicationException(Constants.USER_NOT_FOUND, "用户不存在");
        }

        //验证码校验
        VerifyCode verifyCode = verifyCodeService.queryVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);

        if (verifyCode == null || !verifyCode.getVerifyCode().equals(resetPasswordParam.getVerifyCode())) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "校验码错误");
        }
        //删除验证码
        verifyCodeService.removeVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);
        String encodedPwd = SecurityTools.encode(resetPasswordParam.getPassword());

        //更新密码
        userEntityMapper.updatePassword(userEntity.getId(), encodedPwd);
    }

    @Override
    public LoginResult login(LoginParam loginParam) {
        UserEntity user = userEntityMapper.findByAccount(loginParam.getLoginAccount());
        if (user == null) {
            throw new ApplicationException(Constants.USER_NOT_FOUND, "用户不存在");
        }
        String pswdInDb = user.getPassword();
        String encodedPswd = SecurityTools.encode(loginParam.getPassword());
        if (!pswdInDb.equals(encodedPswd)) {
            throw new ApplicationException(Constants.PASSWORD_WRONG, "密码错误");
        }

        String accessKey = UUIDTools.getUniqueId();
        String refreshKey = UUIDTools.getUniqueId();
        LoginResult result = new LoginResult();
        result.setAccessToken(accessKey);
        result.setRefreshToken(refreshKey);
        result.setUid(user.getSn());
        result.setExpiresAt(-1);

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("accessKey", accessKey);
        keyMap.put("refreshKey", refreshKey);
        redisTemplate.boundHashOps("sessions:" + user.getSn()).putAll(keyMap);
        return result;
    }

    @Override
    public void logout(String uid) {
        redisTemplate.delete("sessions:" + uid);
    }
}
