package com.fansz.members.api.service;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * Created by allan on 15/11/29.
 */
public interface RelationShipService {

    List<FandomInfoResult> findFandomsByMemberSn(String sn,PageBounds pageBounds);

    PageList<FriendInfoResult> getFriends(String uid, PageBounds pageBounds);

    boolean addFriendRequest(AddFriendParam addFriendParam);

    boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add);

    boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree);

    boolean joinFandom(JoinFandomParam joinFandomParam);

    boolean exitFandom(ExitFandomParam joinFandomParam);


    List<UserEntity> getFollowers(FriendsQueryParam param);

    List<UserEntity> getRequesters(FriendsQueryParam param);

    List<UserEntity> getSpecialFriend(FriendsQueryParam param);
}