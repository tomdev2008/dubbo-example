package com.fansz.fandom.service;

import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;
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

    PageList<UserInfoResult> searchMembers(SearchMemberParam searchMemberParam);

    PageList<UserInfoResult> searchMembers(String searchKey,String sn,PageBounds pageBounds);

    List<String> getImages(ContactQueryParam contractQueryParam);

    int isExistsNickname(String nickname,String excludeSn);
}
