package com.fansz.members.model.user;

import com.fansz.members.model.param.UserInfoResult;

/**
 * Created by allan on 15/11/26.
 */
public class FriendResult extends UserInfoResult{

    private String relationship;

    private String friends;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }
}
