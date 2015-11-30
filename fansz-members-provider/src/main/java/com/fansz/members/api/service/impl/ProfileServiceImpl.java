package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.tools.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        UserInfoResult result= BeanTools.copyAs(user, UserInfoResult.class);
        result.setLoginname(null);
        result.setPassword(null);
        result.setSn(null);
        return result;
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        UserEntity user = new UserEntity();
        user.setSn(modifyProfilePara.getUid());
        user.setBirthday(modifyProfilePara.getBirthday());
        user.setGender(modifyProfilePara.getGender());
        user.setNickname(modifyProfilePara.getNickname());
        user.setMemberAvatar(modifyProfilePara.getMemberAvatar());
        userEntityMapper.updateByUidSelective(user);
    }
}