package com.fansz.fandom.service;

import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;
import java.util.Map;

/**
 * 用户配置服务
 */
public interface ProfileService {

    Map<String,String> getProfile(QueryProfileParam queryUserParam);

    void modifyProfile(ModifyProfileParam modifyProfileParam);

    boolean setMemberType(SetMemberParam setMemberParam);

    PageList<UserInfoResult> searchMembers(SearchMemberParam searchMemberParam);

    PageList<UserInfoResult> searchMembers(String searchKey,String sn,PageBounds pageBounds);

    List<String> getImages(ContactQueryParam contractQueryParam);

    boolean isExistsNickname(String nickname,String excludeSn);
}
