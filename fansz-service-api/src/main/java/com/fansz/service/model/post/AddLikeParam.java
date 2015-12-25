package com.fansz.service.model.post;

import com.fansz.common.provider.model.AccessTokenAware;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by LiZhe on 12/4/2015.
 * 点赞传入参数
 */
public class AddLikeParam implements AccessTokenAware {

    @NotBlank
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("member_sn")
    private String currentSn;

    @NotNull
    @JsonProperty("post_id")
    private Long postId;

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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
