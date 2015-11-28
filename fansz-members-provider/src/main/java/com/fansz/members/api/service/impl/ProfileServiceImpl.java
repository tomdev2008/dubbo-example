package com.fansz.members.api.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.FandomMapper;
import com.fansz.members.api.repository.UserEntityMapper;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.FriendResult;
import com.fansz.members.model.profile.ModifyProfileParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    private FandomMapper fandomMapper;

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
        user.setMemberAvatar(modifyProfilePara.getMemberAvatar());
        userEntityMapper.updateByUidSelective(user);
    }

    @Override
    public List<FocusedFandomResult> getFollowedFandoms(String uid) {
        UserInfoResult user = userEntityMapper.findByUid(uid);
        return fandomMapper.findFandomByIds(user.get);
    }

    @Override
    public List<FriendResult> getFriendsInfo(String mobiles) {
        List<String> mobileList = Arrays.asList(mobiles.split(","));

        List<UserInfoResult> users = userEntityMapper.findByMobiles(mobileList);

        List<FriendResult> friendList = new ArrayList<>();
        for (UserInfoResult userInfo : users) {
            FriendResult friend = new FriendResult();
            BeanUtils.copyProperties(userInfo, friend);
            friendList.add(friend);
        }
        return friendList;

    }
}