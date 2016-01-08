package com.fansz.relations.service;

import com.fansz.pub.model.QueryResult;
import com.fansz.relations.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by allan on 15/11/29.
 */
public interface RelationShipService {

    QueryResult<Map<String, String>> getFriends(FriendsQueryParam friendsParam, boolean isSpecial);

    boolean addFriendRequest(AddFriendParam addFriendParam);

    boolean dealSpecialFriend(AddFriendParam addFriendParam, boolean add);

    boolean dealFriendRequest(OpRequestParam opRequestParam, boolean agree);

    QueryResult<Map<String,String>> listAddMeRequest(FriendsQueryParam friendsQueryParam);

    QueryResult<Map<String,String>> listMySendRequest(FriendsQueryParam friendsQueryParam);

    QueryResult<FriendInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam);

    AddContactsRemarkResult addContactsRemark(AddContactsRemarkParam addContactsRemarkParam);
}