package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 发post参数模型
 */
public class AddPostParam implements Serializable {

    private static final long serialVersionUID = -9003165341407262234L;

    private String sn;

    @JsonProperty("fandom_id")
    private Long fandomId;

    @JsonProperty("post_title")
    private String postTitle;

    @JsonProperty("post_content")
    private String postContent;

    @JsonProperty("post_newsfeeds")
    private String postNewsfeeds;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

}
