package com.fansz.members.model.post;

import com.fansz.members.model.AccessTokenAware;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class AddLikeParam implements AccessTokenAware {

    @JsonProperty("access_token")
    private String accessToken;

    @NotBlank
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
