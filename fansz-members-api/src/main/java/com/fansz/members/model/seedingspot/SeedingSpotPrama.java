package com.fansz.members.model.seedingspot;

import com.fansz.members.model.PageParam;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by dell on 2015/12/5.
 */
public class SeedingSpotPrama  extends PageParam implements Serializable{

    private static final long serialVersionUID = -899485784754811854L;

    @JsonProperty("member_sn")
    private String memberSn;
    @JsonProperty("access_token")
    private String accessToken;

    private Long id;
    private String intro;
    private Long priorityNo;
    private String status;
    private String clickUrl;
    private String seedingType;
    private String params;
    private String bgImg;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getPriorityNo() {
        return priorityNo;
    }

    public void setPriorityNo(Long priorityNo) {
        this.priorityNo = priorityNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getSeedingType() {
        return seedingType;
    }

    public void setSeedingType(String seedingType) {
        this.seedingType = seedingType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

}
