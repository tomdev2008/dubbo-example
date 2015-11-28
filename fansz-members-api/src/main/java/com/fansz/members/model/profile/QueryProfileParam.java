package com.fansz.members.model.profile;

/**
 * 查询登陆用户详细信息
 */
public class QueryProfileParam {
    private String uid;

    private String accessToken;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
