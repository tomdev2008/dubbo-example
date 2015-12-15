package com.fansz.members.model.post;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by LiZhe on 12/5/2015.
 */
public class PostsQueryParam {

    @JsonProperty("member_sn")
    private String sn;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("fandom_id")
    private long fandomId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("page_num")
    private int pageNum;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getFandomId() {
        return fandomId;
    }

    public void setFandomId(long fandomId) {
        this.fandomId = fandomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
