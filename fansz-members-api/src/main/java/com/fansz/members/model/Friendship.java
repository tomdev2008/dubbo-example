package com.fansz.members.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 15-10-26.
 */
public class Friendship implements Serializable
{
    private String id;

    // 用户id
    private String userId;

    // 好友列表
    private List<User> friends;

    // 候选人列表
    private List<User> candidates;

    // 主动添加他人列表
    private List<User> requesters;

    public List<User> getRequesters() {
        return requesters;
    }

    public void setRequesters(List<User> requesters) {
        this.requesters = requesters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<User> candidates) {
        this.candidates = candidates;
    }
}
