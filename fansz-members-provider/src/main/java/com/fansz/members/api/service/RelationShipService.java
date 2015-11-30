package com.fansz.members.api.service;

import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.FriendsParam;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

/**
 * Created by allan on 15/11/29.
 */
public interface RelationShipService {

    List<FandomInfoResult> findFandomsByUid(String uid);

   List<UserInfoResult> getFriends(String uid, PageBounds pageBounds);
}
