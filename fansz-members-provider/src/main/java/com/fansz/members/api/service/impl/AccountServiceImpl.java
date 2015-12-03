package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.SessionModel;
import com.fansz.members.api.model.VerifyCodeModel;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.tools.Constants;
import com.fansz.members.tools.VerifyCodeType;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.account.*;
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
 * 会员服务逻辑实现类
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
    public UserEntity register(RegisterParam registerParam) {
        //验证码校验
        VerifyCodeModel verifyCode = verifyCodeService.queryVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        if (verifyCode == null || !verifyCode.getVerifyCode().equals(registerParam.getVerifyCode())) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "验证码错误");
        }
        if (!verifyCodeService.isValid(verifyCode)) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "验证码错误");
        }
        //Remove invalid Code
        verifyCodeService.removeVerifyCode(registerParam.getMobile(), VerifyCodeType.REGISTER);

        UserEntity existedUser = userEntityMapper.findByAccount(registerParam.getLoginname());
        if (existedUser != null) {
            throw new ApplicationException(Constants.USER_EXISTS, "User exists");
        }
        //Create User
        String encodedPwd = SecurityTools.encode(registerParam.getPassword());
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerParam, user);
        user.setSn(UUIDTools.getUniqueId());
        user.setProfileCreatetime(new Date());
        user.setProfileUpdatetime(new Date());
        user.setPassword(encodedPwd);
        user.setMemberStatus(Constants.USER_STATUS_OK);
        logger.info("Begin to add profile " + user);

        //Save User Info
        userEntityMapper.insertSelective(user);
        logger.info("profile saved:" + user);

        return user;
    }

    private void putSessionInRedis(String accessToken, UserEntity user) {
        String key = "session:" + accessToken;
        Map<String, String> session = new HashMap<>();
        session.put("lastAccessTime", String.valueOf(System.currentTimeMillis()));
        session.put("accessToken", UUIDTools.getUniqueId());
        session.put("refreshToken", UUIDTools.getUniqueId());
        session.put("id", String.valueOf(user.getId()));
        session.put("sn", user.getSn());
        //Date expiresAt = DateTools.wrapDate(new Date(), "m+" + MembersConstant.EXPIRED_PERIOD);
        //session.put("expiredAt", String.valueOf(expiresAt.getTime()));
        session.put("expiredAt", "-1");
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
        UserEntity user = userEntityMapper.selectByUid(changePasswordPara.getUid());
        if (user == null) {
            throw new ApplicationException(Constants.USER_NOT_FOUND, "User does't exist");
        }
        if (!user.getPassword().equals(encodedPwd)) {
            throw new ApplicationException(Constants.PASSWORD_WRONG, "Wrong password");
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordPara.getNewPassword());
        UserEntity changeParam = new UserEntity();
        changeParam.setId(user.getId());
        changeParam.setPassword(encodedNewPwd);
        userEntityMapper.updateByPrimaryKeySelective(changeParam);
    }

    /**
     * 重置密码
     *
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) {
        UserEntity user = userEntityMapper.findByMoblie(resetPasswordParam.getMobile());
        if (user == null) {//用户不存在
            throw new ApplicationException(Constants.USER_NOT_FOUND, "用户不存在");
        }

        //验证码校验
        VerifyCodeModel verifyCode = verifyCodeService.queryVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);

        if (verifyCode == null || !verifyCode.getVerifyCode().equals(resetPasswordParam.getVerifyCode())) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "校验码错误");
        }
        if (!verifyCodeService.isValid(verifyCode)) {
            throw new ApplicationException(Constants.VERIFY_ERROR, "验证码错误");
        }
        //删除验证码
        verifyCodeService.removeVerifyCode(resetPasswordParam.getMobile(), VerifyCodeType.RESET);
        String encodedPwd = SecurityTools.encode(resetPasswordParam.getPassword());

        //更新密码
        UserEntity changeParam = new UserEntity();
        changeParam.setId(user.getId());
        changeParam.setPassword(encodedPwd);
        userEntityMapper.updateByPrimaryKeySelective(changeParam);
    }

    @Override
    public LoginResult login(LoginParam loginParam) {
        UserEntity user = userEntityMapper.findByAccount(loginParam.getLoginname());
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

        putSessionInRedis(accessKey, user);
        return result;
    }

    @Override
    public void logout(LogoutParam logoutParam) {
        redisTemplate.delete("sessions:" + logoutParam.getAccessToken());
    }

    @Override
    public SessionModel getSession(String accessToken) {
        String key = "session:" + accessToken;
        Map<Object, Object> sessionMap = redisTemplate.boundHashOps(key).entries();
        if (sessionMap != null) {
            String id = (String) sessionMap.get("id");
            String sn = (String) sessionMap.get("sn");
            String refreshToken = (String) sessionMap.get("refreshToken");
            String lastAccessTime = (String) sessionMap.get("lastAccessTime");

            SessionModel verifyCodeEntity = new SessionModel(Long.valueOf(id), sn, accessToken, refreshToken, Long.valueOf(lastAccessTime));
            return verifyCodeEntity;
        }
        return null;
    }
}
