package com.fansz.members.api.service.impl;

import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.service.MessageService;
import com.fansz.members.api.utils.IdentifyCodeGenerator;
import com.fansz.members.model.IdentifyCode;
import com.fansz.members.model.User;
import com.fansz.members.model.param.RegisterPara;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Created by root on 15-11-3.
 */
@Service
@Log4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IdentifyCodeGenerator identifyCodeGenerator;

    @Autowired
    private IdentifyMapper identifyMapper;

    @Autowired
    private SMSProperties smsProperties;

    @Override
    public User register(RegisterPara registerPara) {

        //Check Identify Code
        IdentifyCode identifyCode = identifyMapper.getIdentifyCode(registerPara.getMobile());
        Assert.notNull(identifyCode, "error.identifyCode.empty");
        Assert.isTrue(identifyCode.getSendCount() > 0, "error.identifyCode.overSendCount");
        Assert.isTrue(identifyCode.getIdentifyCode().equals(registerPara.getIdentifyCode()), "error.identifyCode.wrongCode");
        Assert.isTrue(identifyCode.getExpiredTime().getTime() >= (new Date()).getTime(), "error.identifyCode.expiredCode");

        //Remove invalid Code
        identifyMapper.removeInvalidIdentifyCode(registerPara.getMobile());

        //Create User
        User user = new User(registerPara);
        log.info("Begin to add user " + user);

        //Save User Info
        userMapper.saveUser(user);
        log.info("user saved:" + user);

        return user;
    }

    /**
     * 获取忘记密码验证码
     * @param mobile 手机号码
     */
    @Override
    public void getPasswordIdentifyCode(String mobile) {
        //TODO Check last time of sended Message of the mobile, can not send more messages in one minute.

        //Check Identify Code
        IdentifyCode code = identifyMapper.getIdentifyCode(mobile);
        if (null != code && null != code.getExpiredTime())
        {
            Assert.notNull(code, "error.identifyCode.empty");
        }

        //Check User is exist
        Assert.isTrue(userMapper.isExisted(mobile), "error.register.userNotExist");

        //Generate Identify Code
        String idendify = identifyCodeGenerator.getIdentifyCode();

        //Save Identify Code to DB
        Date currentDate = new Date();
        IdentifyCode identifyCode = new IdentifyCode(mobile, idendify,
                new Date(currentDate.getTime() + smsProperties.getIdentityCodePeriod()) , 10);
        identifyMapper.saveIdentifyCode(identifyCode);

        //Generate Message
        String msg = smsProperties.getPasswordMsg().replace("{code}", identifyCode.getIdentifyCode())
                .replace("{time}", String.valueOf(smsProperties.getPasswordTime()));

        //Send Message
        messageService.sendMessage(mobile, msg);
    }

    @Override
    public void getRegisterIdentifyCode(String mobile) {
        //TODO Check last time of sended Message of the mobile, can not send more messages in one minute.

        //Check User is not exist
        Assert.isTrue(!userMapper.isExisted(mobile), "error.register.userExist");

        //Generate Identify Code
        String idendify = identifyCodeGenerator.getIdentifyCode();

        //Save Identify Code to DB
        Date currentDate = new Date();
        IdentifyCode identifyCode = new IdentifyCode(mobile, idendify,
                new Date(currentDate.getTime() + smsProperties.getIdentityCodePeriod()) , 10);
        identifyMapper.saveIdentifyCode(identifyCode);

        //Generate Message
        String msg = smsProperties.getIdentityCodeMsg().replace("{code}", identifyCode.getIdentifyCode())
                .replace("{time}", String.valueOf(smsProperties.getIdentityCodeTime()));

        //Send Message
        messageService.sendMessage(mobile, msg);
    }

    @Override
    public void changePassword(ChangePasswordPara changePasswordPara) {

        //TODO M2 Need Create Bean
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

        //Get User Info
        User user = userMapper.findByUserId(changePasswordPara.getUserId());
        Assert.notNull(user, "error.register.userNotExist");

        //Check User Old Password
        Assert.isTrue(bCryptPasswordEncoder.matches(changePasswordPara.getPasswordOld(), user.getPassword()), "error.user.wrongPassword");

        //Update New Password
        userMapper.updatePassword(changePasswordPara);
    }

    /**
     * 重置密码接口
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) {

        //Check User is exist
        Assert.isTrue(userMapper.isExisted(resetPasswordParam.getMobile()), "error.register.userNotExist");

        //Check Identify Code
        IdentifyCode identifyCode = identifyMapper.getIdentifyCode(resetPasswordParam.getMobile());
        Assert.notNull(identifyCode, "error.identifyCode.empty");
        Assert.isTrue(identifyCode.getSendCount() > 0, "error.identifyCode.overSendCount");
        Assert.isTrue(identifyCode.getIdentifyCode().equals(resetPasswordParam.getIdentifyCode()), "error.identifyCode.wrongCode");
        Assert.isTrue(identifyCode.getExpiredTime().getTime() >= (new Date()).getTime(), "error.identifyCode.expiredCode");

        //Remove invalid Code
        identifyMapper.removeInvalidIdentifyCode(resetPasswordParam.getMobile());

        //Change Password
        userMapper.resetPassword(resetPasswordParam);
    }
}
