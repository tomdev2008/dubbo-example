package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/4/2015.
 */
public class GetMemberFandomPostsParam {

    @JsonProperty("fandom_id")
    private long fandomId;

    @JsonProperty("member_sn")
    private String memberSn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("page_num")
    private int pageNum;

    @JsonProperty("page_size")
    private int pageSize;

    public long getFandomId() {
        return fandomId;
    }

    public void setFandomId(long fandomId) {
        this.fandomId = fandomId;
    }

    public String getMemberSn() {
        return memberSn;
    }

    public void setMemberSn(String memberSn) {
        this.memberSn = memberSn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
