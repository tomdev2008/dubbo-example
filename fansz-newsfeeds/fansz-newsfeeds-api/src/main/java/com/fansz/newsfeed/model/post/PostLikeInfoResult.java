package com.fansz.newsfeed.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.newsfeed.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class PostLikeInfoResult implements Serializable {

    private static final long serialVersionUID = -8769041146051550921L;

    @JSONField(name="like_id")
    private Long likeId;

    @JSONField(name="like_time")
    private Date likeTime;

    @JSONField(name="like_member")
    private UserInfoResult likeMember;


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
