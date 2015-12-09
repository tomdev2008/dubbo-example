package com.fansz.members.api.service;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.*;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * 用户配置服务
 */
public interface ProfileService {

    UserInfoResult getProfile(QueryProfileParam queryUserParam);

    void modifyProfile(ModifyProfileParam modifyProfileParam);

    int setMemberType(SetMemberParam setMemberParam);

    PageList<ContactInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam);

    PageList<UserInfoResult> searchMembers(UserEntity searchParam,PageBounds pageBounds);

    PageList<UserInfoResult> searchMembers(String searchKey,String sn,PageBounds pageBounds);

    List<String> getImages(ContactQueryParam contractQueryParam);

    int isExistsNickname(String nickname,String excludeSn);
}
