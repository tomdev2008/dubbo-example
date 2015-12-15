package com.fansz.members.model.post;

import com.fansz.members.model.AccessTokenAware;
import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 查询指定会员所有帖子列表request param
 * Created by wukai on 15/12/14.
 */
public class GetMemberPostsParam extends PageParam implements AccessTokenAware {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("friend_sn")
    private String friendSn;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getFriendSn() {
        return friendSn;
    }

    public void setFriendSn(String friendSn) {
        this.friendSn = friendSn;
    }
}
