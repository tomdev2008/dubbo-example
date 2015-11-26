package com.fansz.members.model.friendship;

import java.io.Serializable;

/**
 * 查询用户关注的fandom,传入参数
 */
public class FocusedFandomParam implements Serializable {
    private static final long serialVersionUID = 394668565041221041L;
    private String accessToken;
    private Long uid;
    private int count;
    private int cursor;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }
}
