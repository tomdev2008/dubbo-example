package com.fansz.members.api.provider;

import com.fansz.members.api.RelationShipApi;
import com.fansz.members.api.service.RelationShipService;
import com.fansz.members.tools.Constants;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.relationship.*;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.relationship.FriendsQueryParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 关系provider
 */
@Component("relationShipProvider")
public class RelationShipProvider implements RelationShipApi {

    private final static NullResult PRESENCE = new NullResult();
    @Autowired
    private RelationShipService relationShipService;

    @Override
    public CommonResult<List<FandomInfoResult>> getMemberFandoms(MemberFandomQueryParam fandomParam) {
        CommonResult<List<FandomInfoResult>> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        // 获得我关注的fandom
        PageBounds pageBounds = new PageBounds(fandomParam.getOffset(), fandomParam.getLimit());
        List<FandomInfoResult> fandoms = relationShipService.findFandomsByMemberSn(fandomParam.getSn(), pageBounds);
        result.setResult(fandoms);
        return result;
    }

    @Override
    public CommonPagedResult<List<FriendInfoResult>> getFriends(FriendsQueryParam friendsParam) {
        CommonPagedResult<List<FriendInfoResult>> result = new CommonPagedResult<>();
        result.setStatus(Constants.SUCCESS);
        // 获得好友详细信息
        PageBounds pageBounds = new PageBounds(friendsParam.getOffset(), friendsParam.getLimit());
        PageList<FriendInfoResult> friends = relationShipService.getFriends(friendsParam.getSn(), pageBounds);
        result.setResult(friends);
        result.setTotalNum(friends.getPaginator().getTotalCount());
        return result;

    }

    @Override
    public CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.addFriendRequest(addFriendParam);
        result.setResult(PRESENCE);
        result.setMessage("Add friend request successfully");
        return result;
    }

    @Override
    public CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.dealSpecialFriend(addFriendParam, true);
        result.setResult(PRESENCE);
        result.setMessage("Add special friend  successfully");
        return result;
    }

    @Override
    public CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.dealSpecialFriend(addFriendParam, false);
        result.setResult(PRESENCE);
        result.setMessage("Add special friend  successfully");
        return result;
    }

    @Override
    public CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.dealFriendRequest(opRequestParam, true);
        result.setResult(PRESENCE);
        result.setMessage("Agree friend request successfully");
        return result;
    }

    @Override
    public CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.joinFandom(joinFandomParam);
        result.setResult(PRESENCE);
        result.setMessage("Join fandom successfully");
        return result;
    }

    @Override
    public CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) {
        CommonResult<NullResult> result = new CommonResult<NullResult>();
        result.setStatus(Constants.SUCCESS);
        relationShipService.exitFandom(exitFandomParam);
        result.setResult(PRESENCE);
        result.setMessage("Exit fandom successfully");
        return result;
    }
}