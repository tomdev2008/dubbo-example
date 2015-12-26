package com.fansz.fandom.model.relationship;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AbstractToken;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加好友请求参数模型
 */
public class AddFriendParam extends AbstractToken {

    @NotBlank
    @JSONField(name="member_sn")
    private String currentSn;

    @NotBlank
    @JSONField(name="friend_sn")
    private String friendMemberSn;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public String getFriendMemberSn() {
        return friendMemberSn;
    }

    public void setFriendMemberSn(String friendMemberSn) {
        this.friendMemberSn = friendMemberSn;
    }

}
