package com.fansz.members.api.provider;

import com.fansz.members.api.FriendShipApi;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.friendship.FocusedFandomParam;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.user.FriendResult;
import com.fansz.members.model.user.FriendsParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by allan on 15/11/26.
 */
@Component("friendShipProvider")
public class FriendShipProvider implements FriendShipApi {

    private ProfileService profileService;

    @Override
    public CommonResult<List<FocusedFandomResult>> getFandoms(FocusedFandomParam fandomParam) {
        CommonResult<List<FocusedFandomResult>> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        try {
            // 获得我关注的fandom
            List<FocusedFandomResult> fandoms = profileService.getFollowedFandoms(fandomParam.getUid());
            result.setResult(fandoms);
        } catch (Exception e) {
            result.setStatus(Constants.FAIL);
        }
        return result;
    }


    public CommonResult<List<FriendResult>> getFriends(FriendsParam friendsParam) {
        CommonResult<List<FriendResult>> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        try {
            // 获得联系人详细信息
            List<FriendResult> friends = profileService.getFriendsInfo(friendsParam.getMobiles());
            result.setResult(friends);
            result.setStatus(Constants.FAIL);

        } catch (Exception e) {
            result.setStatus("1");
        }
        return result;

    }
}