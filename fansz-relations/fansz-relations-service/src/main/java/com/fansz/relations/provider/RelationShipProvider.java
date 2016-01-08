package com.fansz.relations.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.pub.model.QueryResult;
import com.fansz.relations.api.RelationShipApi;
import com.fansz.relations.model.*;
import com.fansz.relations.service.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 关系provider
 */
@Component("relationShipProvider")
public class RelationShipProvider extends AbstractProvider implements RelationShipApi {

    @Autowired
    private RelationShipService relationShipService;


    @Override
    public CommonPagedResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam) throws ApplicationException {
        // 获得好友详细信息
        QueryResult<FriendInfoResult> friends = relationShipService.getFriends(friendsParam, false);
        return renderPagedSuccess(friends);
    }

    @Override
    public CommonPagedResult<FriendInfoResult> getSpecialFriends(FriendsQueryParam friendsParam) throws ApplicationException {
        // 获得特别好友详细信息
        QueryResult<FriendInfoResult> friends = relationShipService.getFriends(friendsParam, true);
        return renderPagedSuccess(friends);
    }

    @Override
    public CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam) throws ApplicationException {
        relationShipService.addFriendRequest(addFriendParam);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam) throws ApplicationException {
        relationShipService.dealSpecialFriend(addFriendParam, true);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam) throws ApplicationException {
        relationShipService.dealSpecialFriend(addFriendParam, false);
        return renderSuccess();
    }

    @Override
    public CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam) throws ApplicationException {
        relationShipService.dealFriendRequest(opRequestParam, true);
        return renderSuccess();
    }

    @Override
    public CommonPagedResult<FriendInfoResult> getFriendRquests(FriendsQueryParam friendsQueryParam) throws ApplicationException {
        QueryResult<FriendInfoResult> dataResult = relationShipService.listAddMeRequest(friendsQueryParam);
        return renderPagedSuccess(dataResult);
    }

    @Override
    public CommonPagedResult<FriendInfoResult> getRequesters(FriendsQueryParam friendsQueryParam) throws ApplicationException {
        QueryResult<FriendInfoResult> dataResult = relationShipService.listMySendRequest(friendsQueryParam);
        return renderPagedSuccess(dataResult);
    }

    @Override
    public CommonPagedResult<FriendInfoResult> getContactInfo(ContactQueryParam contractQueryParam) throws ApplicationException {
        QueryResult<FriendInfoResult> dataResult = relationShipService.findRelationByMobiles(contractQueryParam);
        return super.renderPagedSuccess(dataResult);
    }

    @Override
    public CommonResult<AddContactsRemarkResult> addContactsRemark(AddContactsRemarkParam addContactsRemarkParam) throws ApplicationException {
        AddContactsRemarkResult addContactsRemarkResult = relationShipService.addContactsRemark(addContactsRemarkParam);
        return super.renderSuccess(addContactsRemarkResult);
    }
}