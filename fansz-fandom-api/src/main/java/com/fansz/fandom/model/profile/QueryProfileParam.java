package com.fansz.fandom.model.profile;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;

/**
 * 查询登陆用户详细信息
 */
public class QueryProfileParam extends AbstractToken {

    private static final long serialVersionUID = 784997167533858716L;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="friend_sn")
    private String friendSn;


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
