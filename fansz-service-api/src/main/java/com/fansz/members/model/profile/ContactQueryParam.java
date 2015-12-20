package com.fansz.members.model.profile;

import com.fansz.members.model.AccessTokenAware;
import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 上传通讯录,查询联系人好友参数模型
 */
public class ContactQueryParam extends PageParam implements AccessTokenAware {


    @JsonProperty("mobile_list")
    private List<String> mobileList;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("friend_sn")
    private String friendSn;

    public List<String> getMobileList() {
        return mobileList;
    }

    public void setMobileList(List<String> mobileList) {
        this.mobileList = mobileList;
    }

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
