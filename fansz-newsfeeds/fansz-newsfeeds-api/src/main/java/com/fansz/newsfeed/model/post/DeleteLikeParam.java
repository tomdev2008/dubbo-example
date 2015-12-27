package com.fansz.newsfeed.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by LiZhe on 12/4/2015.
 * 取消点赞传入参数
 */
public class DeleteLikeParam implements AccessTokenAware {

    @JSONField(name="access_token")
    private String accessToken;

    @NotBlank
    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="like_id")
    private Long likeId;

    @NotNull
    @JSONField(name="post_id")
    private Long postId;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
