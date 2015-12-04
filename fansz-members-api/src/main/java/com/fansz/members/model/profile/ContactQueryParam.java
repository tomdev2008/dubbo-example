package com.fansz.members.model.profile;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 上传通讯录,查询联系人好友参数模型
 */
public class ContactQueryParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 2427269296809924617L;

    @JsonProperty("mobile_list")
    private List<String> mobileList;

    @JsonProperty("accessToken")
    private String accessToken;

    private String sn;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("friend_sn")
    private String friendSn;

    public List<String> getMobileList() {
        return mobileList;
    }

    public void setMobileList(List<String> mobileList) {
        this.mobileList = mobileList;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getFriendSn() {
        return friendSn;
    }

    public void setFriendSn(String friendSn) {
        this.friendSn = friendSn;
    }
}
