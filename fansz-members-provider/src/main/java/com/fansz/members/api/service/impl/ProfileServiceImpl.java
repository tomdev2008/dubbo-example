package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.param.UserInfoResult;
import com.fansz.members.model.user.FriendResult;
import com.fansz.members.model.user.ModifyProfileParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置服务实现层
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public UserInfoResult getProfile(String uid) {
        return userEntityMapper.findByUid(uid);
    }

    @Override
    public void modifyProfile(ModifyProfileParam modifyProfilePara) {
        UserEntity user = new UserEntity();
        user.setSn(modifyProfilePara.getUid());
        user.setBirthday(modifyProfilePara.getBirthday());
        user.setGender(modifyProfilePara.getGender());
        user.setNickname(modifyProfilePara.getNickName());
        userEntityMapper.updateByUidSelective(user);
    }

    @Override
    public List<FocusedFandomResult> getFollowedFandoms(String uid) {
        UserInfoResult user = userEntityMapper.findByUid(uid);
        return userEntityMapper.findFandomById(user.getId());
    }

    @Override
    public List<FriendResult> getFriendsInfo(String mobiles) {
        List<String> mobileList = Arrays.asList(mobiles.split(","));

        List<UserInfoResult> users = userEntityMapper.findByMobiles(mobileList);

        List<FriendResult> friendList=new ArrayList<>();
        for(UserInfoResult userInfo:users){
            FriendResult friend=new FriendResult();
            BeanUtils.copyProperties(userInfo,friend);
            friendList.add(friend);
        }
        return friendList;
    }
}
