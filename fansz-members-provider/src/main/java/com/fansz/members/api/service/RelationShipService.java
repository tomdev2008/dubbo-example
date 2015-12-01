package com.fansz.members.api.service;

import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.AddFriendParam;
import com.fansz.members.model.relationship.FriendInfoResult;
import com.fansz.members.model.relationship.OpRequestParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * Created by allan on 15/11/29.
 */
public interface RelationShipService {

    List<FandomInfoResult> findFandomsByUid(String uid);

    PageList<FriendInfoResult> getFriends(String uid, PageBounds pageBounds);

    boolean addFriendRequest(AddFriendParam addFriendParam);

    boolean dealSpecialFriend(AddFriendParam addFriendParam,boolean add);

    boolean dealFriendRequest(OpRequestParam opRequestParam,boolean agree);

}