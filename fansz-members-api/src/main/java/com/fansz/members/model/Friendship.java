package com.fansz.members.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 15-10-26.
 */
@Data
@Document(collection = "friendship")
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
