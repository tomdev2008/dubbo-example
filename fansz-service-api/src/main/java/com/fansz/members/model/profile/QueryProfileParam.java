package com.fansz.members.model.profile;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 查询登陆用户详细信息
 */
public class QueryProfileParam extends AbstractToken {

    private static final long serialVersionUID = 784997167533858716L;

    @JsonProperty("member_sn")
    private String currentSn;

    @JsonProperty("friend_sn")
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
