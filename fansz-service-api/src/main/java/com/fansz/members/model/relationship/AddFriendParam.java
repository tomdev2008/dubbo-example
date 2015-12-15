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
    private String myMemberSn;

    @NotBlank
    @JsonProperty("friend_sn")
    private String friendMemberSn;


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

}
