package com.fansz.members.api.provider;

import com.fansz.members.api.FriendShipApi;
import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.friendship.FocusedFandomParam;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.user.FriendResult;
import com.fansz.members.model.user.FriendsParam;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by allan on 15/11/26.
 */
public class FriendShipProvider implements FriendShipApi {

    private ProfileService profileService;

    @Override
    public CommonResult<List<FocusedFandomResult>> getFandoms(FocusedFandomParam fandomParam) {
        CommonResult<List<FocusedFandomResult>> result = new CommonResult<>();
        result.setStatus("0");
        try {
            // 获得我关注的fandom
            List<FocusedFandomResult> fandoms = profileService.getFollowedFandoms(fandomParam.getUid());
            result.setResult(fandoms);
        } catch (Exception e) {
            result.setStatus("1");
        }
        return result;
    }


    public CommonResult<List<FriendResult>> getFriends(FriendsParam friendsParam) {
        CommonResult<List<FriendResult>> result = new CommonResult<>();
        try {
            // 获得所有联系人信息
            List<FriendResult> friends = profileService.getFriends(friendsParam.getUid());
            result.setResult(friends);

        } catch (Exception e)

        {
            result.setStatus("1");
        }
        return result;

    }
}