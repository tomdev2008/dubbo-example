package com.fansz.newsfeed.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 发post传入参数
 */
public class AddPostParam implements AccessTokenAware, Serializable {

    private static final long serialVersionUID = -9003165341407262234L;

    @JSONField(name="access_token")
    private String accessToken;

    @JSONField(name="member_sn")
    private String currentSn;

    @JSONField(name="fandom_id")
    private Long fandomId;

    @JSONField(name="post_title")
    private String postTitle;

    @NotBlank
    @JSONField(name="post_content")
    private String postContent;

    @JSONField(name="post_newsfeeds")
    private String postNewsfeeds;


    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
    }

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostNewsfeeds() {
        return postNewsfeeds;
    }

    public void setPostNewsfeeds(String postNewsfeeds) {
        this.postNewsfeeds = postNewsfeeds;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
