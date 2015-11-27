package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.model.VerifyCode;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.AccountService;
import com.fansz.members.api.service.MessageService;
import com.fansz.members.api.service.VerifyCodeService;
import com.fansz.members.model.account.ChangePasswordParam;
import com.fansz.members.model.account.RegisterParam;
import com.fansz.members.model.account.ResetPasswordParam;
import com.fansz.members.tools.SecurityTools;
import com.fansz.members.api.utils.IdentifyCodeGenerator;
import com.fansz.members.model.param.RegisterPara;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-3.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private MessageService messageService;

    @Override
    public UserEntity register(RegisterParam registerParam) {

        //Check Identify Code
        VerifyCode identifyCode = verifyCodeService.getVerifyCode(registerParam.getMobile(),"register");

        //Remove invalid Code
        verifyCodeService.removeVerifyCode(registerParam.getMobile(),"register");

        //Create User
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerParam,user);
        logger.info("Begin to add user " + user);

        //Save User Info
        userEntityMapper.insertSelective(user);
        logger.info("user saved:" + user);

        return user;
    }

    /**
     * 获取忘记密码验证码
     * @param changePasswordPara 手机号码
     */


    @Override
    public void changePassword(ChangePasswordParam changePasswordPara) {

        String encodedPwd = SecurityTools.encode(changePasswordPara.getOldPassword());

        //Get User Info
        UserEntity user = userEntityMapper.selectByPrimaryKey(changePasswordPara.getUid());
        if(user==null||!user.getPassword().equals(encodedPwd)){
            throw new RuntimeException("用户不存在");
        }
        //Update New Password
        String encodedNewPwd = SecurityTools.encode(changePasswordPara.getNewPassword());
        userEntityMapper.updatePassword(changePasswordPara.getUid(),encodedNewPwd);
    }

    /**
     * 重置密码接口
     * @param resetPasswordParam 密码对象
     */
    @Override
    public void resetPassword(ResetPasswordParam resetPasswordParam) {
        UserEntity userEntity=userEntityMapper.findByMoblie(resetPasswordParam.getMobile());
        if(userEntity==null){//用户不存在
            throw new RuntimeException("用户不存在");
        }

        //Check Identify Code
        VerifyCode identifyCode = verifyCodeService.getVerifyCode(resetPasswordParam.getMobile(),"reset");

        if(identifyCode==null||!identifyCode.getVerifyCode().equals(resetPasswordParam.getVerifyCode())){
            throw new RuntimeException("校验码错误");
        }
        //Remove invalid Code
        verifyCodeService.removeVerifyCode(resetPasswordParam.getMobile(),"reset");
        String encodedPwd = SecurityTools.encode(resetPasswordParam.getPassword());

        //Change Password
        userEntityMapper.updatePassword(userEntity.getId(),encodedPwd);
    }
}
