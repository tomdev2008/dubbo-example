package com.fansz.members.model.relationship;

import com.fansz.members.model.AbstractToken;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 添加好友请求参数模型
 */
public class AddFriendParam extends AbstractToken {

    @NotBlank
    @JsonProperty("member_sn")
    private String currentSn;

    @NotBlank
    @JsonProperty("friend_sn")
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
