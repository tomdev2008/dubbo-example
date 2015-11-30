package com.fansz.members.api.provider;

import com.fansz.members.api.RelationShipApi;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.service.RelationShipService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.FavoriteFandomParam;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.FriendResult;
import com.fansz.members.model.profile.FriendsParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by allan on 15/11/26.
 */
@Component("relationShipProvider")
public class RelationShipProvider implements RelationShipApi {

    @Autowired
    private RelationShipService relationShipService;

    @Override
    public CommonResult<List<FandomInfoResult>> getFandoms(FavoriteFandomParam fandomParam) {
        CommonResult<List<FandomInfoResult>> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        // 获得我关注的fandom
        List<FandomInfoResult> fandoms = relationShipService.findFandomsByUid(fandomParam.getUid());
        result.setResult(fandoms);
        return result;
    }

    @Override
    public CommonResult<List<UserInfoResult>> getFriends(FriendsParam friendsParam) {
        CommonResult<List<UserInfoResult>> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        // 获得好友详细信息
        PageBounds pageBounds=new PageBounds(friendsParam.getOffset(),friendsParam.getLimit());
        List<UserInfoResult> friends = relationShipService.getFriends(friendsParam.getUid(),pageBounds);
        result.setResult(friends);
        return result;

    }
}