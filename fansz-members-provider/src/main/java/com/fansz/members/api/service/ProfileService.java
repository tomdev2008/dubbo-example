package com.fansz.members.api.service;

import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.model.profile.ContactQueryParam;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * 用户配置服务
 */
public interface ProfileService {

    UserInfoResult getProfile(String uid);

    void modifyProfile(ModifyProfileParam modifyProfileParam);

    List<UserInfoResult> getProfileByNickname(ModifyProfileParam modifyProfileParam);

    PageList<ContactInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam);

    PageList<UserInfoResult> searchMembers(SearchParam searchParam);
}
