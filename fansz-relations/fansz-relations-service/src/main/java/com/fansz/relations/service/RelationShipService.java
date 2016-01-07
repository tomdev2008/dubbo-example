package com.fansz.relations.service;

import com.fansz.pub.model.QueryResult;
import com.fansz.relations.model.*;

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

    QueryResult<FriendInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam);

    AddContactsRemarkResult addContactsRemark(AddContactsRemarkParam addContactsRemarkParam);
}