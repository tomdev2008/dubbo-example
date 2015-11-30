package com.fansz.members.api.service;

import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.ModifyProfileParam;

/**
 * 用户配置服务
 */
public interface ProfileService {

    UserInfoResult getProfile(String uid);

    void modifyProfile(ModifyProfileParam modifyProfileParam);

}
