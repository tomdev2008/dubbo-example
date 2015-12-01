package com.fansz.members.model.relationship;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/30.
 */
public class AddFriendParam implements Serializable {
    private static final long serialVersionUID = 8278115962641637973L;

    @JsonProperty("member_sn")
    private String myMemberSn;

    @JsonProperty("friend_sn")
    private String friendMemberSn;

    @JsonProperty("access_token")
    private String accessToken;

    public String getMyMemberSn() {
        return myMemberSn;
    }

    public void setMyMemberSn(String myMemberSn) {
        this.myMemberSn = myMemberSn;
    }

    public String getFriendMemberSn() {
        return friendMemberSn;
    }

    public void setFriendMemberSn(String friendMemberSn) {
        this.friendMemberSn = friendMemberSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
