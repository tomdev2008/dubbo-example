package com.fansz.relations.service;

import com.fansz.pub.model.QueryResult;
import com.fansz.service.model.relationship.AddFriendParam;
import com.fansz.service.model.relationship.FriendInfoResult;
import com.fansz.service.model.relationship.FriendsQueryParam;
import com.fansz.service.model.relationship.OpRequestParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by allan on 15/11/29.
 */
public interface RelationShipService {

    QueryResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam, boolean isSpecial);

    boolean addFriendRequest(AddFriendParam addFriendParam);

    boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add);

    boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree);

    QueryResult<FriendInfoResult> listAddMeRequest(FriendsQueryParam friendsQueryParam);

    QueryResult<FriendInfoResult> listMySendRequest(FriendsQueryParam friendsQueryParam);

}