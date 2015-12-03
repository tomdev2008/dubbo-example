package com.fansz.members.model.post;

import com.fansz.members.model.profile.UserInfoResult;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class PostLikeInfoResult implements Serializable {

    private static final long serialVersionUID = -8769041146051550921L;
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("like_id")
    private Long likeId;

    @JsonProperty("like_time")
    private Date likeTime;

    @JsonProperty("like_member")
    private UserInfoResult likeMember;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public UserInfoResult getLikeMember() {
        return likeMember;
    }

    public void setLikeMember(UserInfoResult likeMember) {
        this.likeMember = likeMember;
    }
}
