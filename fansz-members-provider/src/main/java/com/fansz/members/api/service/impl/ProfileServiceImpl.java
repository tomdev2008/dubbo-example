package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.tools.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户配置信息服务实现层
 */
@Service()
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public UserInfoResult getProfile(String uid) {
        UserEntity user = userEntityMapper.selectByUid(uid);
        UserInfoResult result = BeanTools.copyAs(user, UserInfoResult.class);
        return result;
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        UserEntity user = BeanTools.copyAs(modifyProfilePara, UserEntity.class);
        user.setProfileUpdatetime(new Date());
        int updated=userEntityMapper.updateByUid(user);
        if(updated!=1){
            throw new ApplicationException(Constants.USER_NOT_FOUND,"User does't exist");
        }
    }
}