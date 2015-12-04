package com.fansz.members.api.provider;

import com.fansz.members.api.RelationShipApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.RelationShipService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.*;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 关系provider
 */
@Component("relationShipProvider")
public class RelationShipProvider extends AbstractProvider implements RelationShipApi {

    @Autowired
    private RelationShipService relationShipService;


    @Override
    public CommonPagedResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam) {
        // 获得好友详细信息
        PageBounds pageBounds = new PageBounds(friendsParam.getOffset(), friendsParam.getLimit());
        PageList<FriendInfoResult> friends = relationShipService.getFriends(friendsParam.getSn(), pageBounds,false);
        return renderPagedSuccess(friends);

    }

    @Override
    public CommonPagedResult<FriendInfoResult> getSpecialFriends(FriendsQueryParam friendsParam) {
        // 获得特别好友详细信息
        PageBounds pageBounds = new PageBounds(friendsParam.getOffset(), friendsParam.getLimit());
        PageList<FriendInfoResult> friends = relationShipService.getFriends(friendsParam.getSn(), pageBounds,true);
        return renderPagedSuccess(friends);
    }

    @Override
    public CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam) {
        relationShipService.addFriendRequest(addFriendParam);
        return renderSuccess(PRESENCE, "Add friend request successfully");
    }

    @Override
    public CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam) {
        relationShipService.dealSpecialFriend(addFriendParam, true);
        return renderSuccess(PRESENCE, "Add special friend  successfully");
    }

    @Override
    public CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam) {
        relationShipService.dealSpecialFriend(addFriendParam, false);
        return renderSuccess(PRESENCE, "Add special friend  successfully");
    }

    @Override
    public CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam) {
        relationShipService.dealFriendRequest(opRequestParam, true);
        return renderSuccess(PRESENCE, "Agree friend request successfully");
    }

    @Override
    public CommonPagedResult<FriendInfoResult> getFriendRquests(FriendsQueryParam friendsQueryParam) {
        PageList<FriendInfoResult> dataResult = relationShipService.listAddMeRequest(friendsQueryParam);
        return renderPagedSuccess(dataResult);
    }

    /**
     * 获取我添加的好友请求
     *
     * @param friendsQueryParam
     * @return
     */
    @Override
    public CommonPagedResult<FriendInfoResult> getRequesters(FriendsQueryParam friendsQueryParam) {
        PageList<FriendInfoResult> dataResult = relationShipService.listMySendRequest(friendsQueryParam);
        return renderPagedSuccess(dataResult);
    }
}