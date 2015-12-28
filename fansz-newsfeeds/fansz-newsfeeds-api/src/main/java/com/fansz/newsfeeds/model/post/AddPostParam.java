package com.fansz.newsfeeds.model.post;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.common.provider.model.AccessTokenAware;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 朋友圈发post传入参数
 */
public class AddPostParam implements AccessTokenAware, Serializable {

    private static final long serialVersionUID = 8343923749399762119L;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "member_sn")
    private String currentSn;

    @JSONField(name = "post_title")
    private String postTitle;

    @NotBlank
    @JSONField(name = "post_content")
    private String postContent;

    @Override
    public String getCurrentSn() {
        return currentSn;
    }

    @Override
    public void setCurrentSn(String currentSn) {
        this.currentSn = currentSn;
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


    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
