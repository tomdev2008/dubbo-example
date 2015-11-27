package com.fansz.members.model;

/**
 * Created by root on 15-10-26.
 */

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(String userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public String getUserIdAgree() {
        return userIdAgree;
    }

    public void setUserIdAgree(String userIdAgree) {
        this.userIdAgree = userIdAgree;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(long agreeTime) {
        this.agreeTime = agreeTime;
    }

    public String getAgreeNickName() {
        return agreeNickName;
    }

    public void setAgreeNickName(String agreeNickName) {
        this.agreeNickName = agreeNickName;
    }

    public String getAddNickName() {
        return addNickName;
    }

    public void setAddNickName(String addNickName) {
        this.addNickName = addNickName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsShowCommunity() {
        return isShowCommunity;
    }

    public void setIsShowCommunity(int isShowCommunity) {
        this.isShowCommunity = isShowCommunity;
    }

    public int getIsSeeCommunity() {
        return isSeeCommunity;
    }

    public void setIsSeeCommunity(int isSeeCommunity) {
        this.isSeeCommunity = isSeeCommunity;
    }

    public int getIsShowTopic() {
        return isShowTopic;
    }

    public void setIsShowTopic(int isShowTopic) {
        this.isShowTopic = isShowTopic;
    }

    public int getIsSeeTopic() {
        return isSeeTopic;
    }

    public void setIsSeeTopic(int isSeeTopic) {
        this.isSeeTopic = isSeeTopic;
    }
}
