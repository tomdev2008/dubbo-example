package com.fansz.auth.service.impl;


import com.fansz.auth.model.*;
import com.fansz.auth.service.AccountService;
import com.fansz.auth.service.SessionService;
import com.fansz.auth.service.VerifyCodeService;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.db.entity.User;
import com.fansz.db.repository.UserDAO;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.SecurityTools;
import com.fansz.pub.utils.UUIDTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Date;
import java.util.Map;

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

    @Autowired
    private JedisTemplate jedisTemplate;

    private final static String USER_STATUS_OK = "1";

    /**
     * 用户注册
     *
     * @param registerParam
     */
    @Override
    public void register(RegisterParam registerParam)  throws ApplicationException {
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

        User existedUser = userDAO.findByAccount(registerParam.getLoginname());
        if (existedUser != null) {
            throw new ApplicationException(ErrorCode.USER_EXISTS.getCode(), ErrorCode.USER_EXISTS.getName());
        }
        //Create User
        String encodedPwd = SecurityTools.encode(registerParam.getPassword());
        final User user = new User();
        BeanUtils.copyProperties(registerParam, user);
        user.setSn(UUIDTools.generate());
        user.setProfileCreatetime(new Date());
        user.setProfileUpdatetime(new Date());
        user.setPassword(encodedPwd);
        user.setMemberStatus(USER_STATUS_OK);
        userDAO.save(user);

        saveUserInRedis(user);//将用户信息保存到redis
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
        User user = userDAO.findBySn(changePasswordParam.getCurrentSn());
        if (user == null) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        if (!user.getPassword().equals(encodedPwd)) {
            throw new ApplicationException(ErrorCode.PASSWORD_WRONG);
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordParam.getNewPassword());
        userDAO.updatePassword(user.getId(), encodedNewPwd);

        syncDataInRedis(user.getSn(), encodedNewPwd);//更新redis中的密码
    }

    /**
     * 重置密码
     *
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) throws ApplicationException {
        User user = userDAO.findByMobile(resetPasswordParam.getMobile());
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

        syncDataInRedis(user.getSn(), encodedPwd);//更新redis中的密码
    }

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     */
    @Override
    public LoginResult login(LoginParam loginParam) throws ApplicationException {
        User user = userDAO.findByAccount(loginParam.getLoginname());
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
        Long expireAt = sessionService.saveSession(accessKey, refreshKey, user.getId(), user.getSn());

        LoginResult result = BeanTools.copyAs(user, LoginResult.class);
        result.setAccessToken(accessKey);
        result.setRefreshToken(refreshKey);
        result.setExpiresAt(expireAt);

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

    /**
     * 同步修改redis中的密码
     *
     * @param sn
     * @param passwd
     */
    private void syncDataInRedis(final String sn, final String passwd) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.hset("user:{" + sn + "}", "password", passwd);
                return true;
            }
        });

    }

    /**
     * 将用户信息保存到redis,目前用户信息在数据库和redis中都保存了一份,后续会统一保存到redis;
     *
     * @param user
     */
    private void saveUserInRedis(final User user) {
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Map<String, Object> userMap = JsonHelper.convertJSONString2Object(JsonHelper.convertObject2JSONString(user), Map.class);
                Pipeline pipe = jedis.pipelined();
                String key = "user:{" + user.getSn() + "}";
                for (Map.Entry<String, Object> prop : userMap.entrySet()) {
                    if (prop.getValue() != null) {
                        pipe.hset(key, prop.getKey(), String.valueOf(prop.getValue()));
                    }
                }
                pipe.sync();
                return true;
            }
        });
    }
}
