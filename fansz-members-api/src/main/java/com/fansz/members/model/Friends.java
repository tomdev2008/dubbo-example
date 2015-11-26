package com.fansz.members.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by root on 15-10-26.
 */
@Data
@NoArgsConstructor
@Document(collection = "friends")
public class Friends {

    private String id;

    private String userIdAdd;

    private String userIdAgree;

    private long createTime;

    private long agreeTime;

    private String agreeNickName;

    private String addNickName;

    private String from;

    private String groupId;

    private String status;

    private int isShowCommunity;

    private int isSeeCommunity;

    private int isShowTopic;

    private int isSeeTopic;

}
