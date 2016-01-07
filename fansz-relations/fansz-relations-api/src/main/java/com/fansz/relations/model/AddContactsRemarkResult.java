package com.fansz.relations.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by dell on 2016/1/7.
 */
public class AddContactsRemarkResult implements Serializable {

    private static final long serialVersionUID = 1579649202696942525L;

    private Long id;
    @JSONField(name="member_sn")
    private String myMemberSn;
    @JSONField(name="friend_sn")
    private String friendMemberSn;
    @JSONField(name="remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
