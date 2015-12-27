package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

/**
 * 查询指定会员所有帖子列表request param
 * Created by wukai on 15/12/14.
 */
public class GetMemberPostsParam extends PageParam implements AccessTokenAware {

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="friend_sn")
    private String friendSn;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getFriendSn() {
        return friendSn;
    }

    public void setFriendSn(String friendSn) {
        this.friendSn = friendSn;
    }
}
