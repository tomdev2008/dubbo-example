package com.fansz.fandom.model.specialfocus;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.profile.UserInfoResult;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFocusResult implements Serializable {

    private static final long serialVersionUID = 5119301795049966905L;

    @JSONField(name="special_id")
    private Long id;

    @JSONField(name="postion_tag")
    private Long postionTag;

    @JSONField(name="special_fandom")
    private FandomInfoResult fandomInfoResult;

    @JSONField(name="special_member")
    private UserInfoResult userInfoResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostionTag() {
        return postionTag;
    }

    public void setPostionTag(Long postionTag) {
        this.postionTag = postionTag;
    }

    public FandomInfoResult getFandomInfoResult() {
        return fandomInfoResult;
    }

    public void setFandomInfoResult(FandomInfoResult fandomInfoResult) {
        this.fandomInfoResult = fandomInfoResult;
    }

    public UserInfoResult getUserInfoResult() {
        return userInfoResult;
    }

    public void setUserInfoResult(UserInfoResult userInfoResult) {
        this.userInfoResult = userInfoResult;
    }
}
