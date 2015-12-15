package com.fansz.members.model.specialfocus;

import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/9.
 */
public class SpecialFocusResult implements Serializable {

    private static final long serialVersionUID = 5119301795049966905L;

    @JsonProperty("special_id")
    private Long id;

    @JsonProperty("postion_tag")
    private Long postionTag;

    @JsonProperty("special_fandom")
    private FandomInfoResult fandomInfoResult;

    @JsonProperty("special_member")
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
