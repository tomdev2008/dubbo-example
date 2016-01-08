package com.fansz.relations.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import com.fansz.common.provider.model.PageParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 上传通讯录,查询联系人好友参数模型
 */
public class ContactQueryParam extends PageParam implements AccessTokenAware {


    @Size(min=1,max=500)
    @NotNull
    @JSONField(name="mobile_list")
    private List<String> mobileList;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="friend_sn")
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
