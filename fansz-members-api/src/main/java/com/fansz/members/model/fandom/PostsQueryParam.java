package com.fansz.members.model.fandom;


import com.fansz.members.model.param.PagePara;

/**
 * Created by root on 15-11-19.
 */
public class PostsQueryParam extends PagePara {

    private Long fandomId;

    private String accessToken;

    private String category;

    public Long getFandomId() {
        return fandomId;
    }

    public void setFandomId(Long fandomId) {
        this.fandomId = fandomId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
