package com.fansz.db.model;

import com.fansz.db.entity.FandomPost;

import java.io.Serializable;

/**
 *
 * 朋友圈fandom post动态需要显示fandom的信息
 * Created by wukai on 15/12/30.
 */
public class NewsFeedsFandomPostVO extends FandomPost implements Serializable {

    private String fandomName;

    private String fandomAvatarUrl;

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }
}
