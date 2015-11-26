package com.fansz.members.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 15-10-26.
 */
@Data
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

}
