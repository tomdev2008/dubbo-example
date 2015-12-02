package com.fansz.members.api.service;

import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.ModifyProfileParam;

import java.util.List;

/**
 * 用户配置服务
 */
public interface ProfileService {

    UserInfoResult getProfile(String uid);

    void modifyProfile(ModifyProfileParam modifyProfileParam);

    List<UserInfoResult> getProfileByNickname(ModifyProfileParam modifyProfileParam);

}
