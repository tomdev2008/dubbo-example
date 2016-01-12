package com.fansz.fandom.model.fandom;

import com.alibaba.fastjson.annotation.JSONField;
import com.fansz.fandom.model.profile.UserInfoResult;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户关注的fandom信息
 */
public class FandomInfoResult implements Serializable {

    private static final long serialVersionUID = -6948988720607403742L;

    private Long id;

    @JSONField(name="fandom_name")
    private String fandomName;

    @JSONField(name="fandom_parent_id")
    private Long fandomParentId;

    @JSONField(name="fandom_parent_name")
    private String fandomParentName;

    @JSONField(name="fandom_admin_sn")
    private String fandomAdminSn;

    @JSONField(name="fandom_creator_sn")
    private String fandomCreatorSn;

    @JSONField(name="fandom_avatar_url")
    private String fandomAvatarUrl;

    @JSONField(name="fandom_intro")
    private String fandomIntro;

    @JSONField(name="fandom_create_time")
    private Date fandomCreateTime;

    @JSONField(name="follower_count")
    private Integer followerCount;

    @JSONField(name="post_count")
    private Integer postCount;

    @JSONField(name="followed")
    private Integer followed;

    @JSONField(name="creator")
    private UserInfoResult creator;

    @JSONField(name="fandom_tag_list")
    private List<FandomTagResult> fandomTagResultList;

    @JSONField(name="fandom_parent_info")
    private Map<String,Object> fandomParentInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public Long getFandomParentId() {
        return fandomParentId;
    }

    public void setFandomParentId(Long fandomParentId) {
        this.fandomParentId = fandomParentId;
    }


    public String getFandomAvatarUrl() {
        return fandomAvatarUrl;
    }

    public void setFandomAvatarUrl(String fandomAvatarUrl) {
        this.fandomAvatarUrl = fandomAvatarUrl;
    }

    public String getFandomIntro() {
        return fandomIntro;
    }

    public void setFandomIntro(String fandomIntro) {
        this.fandomIntro = fandomIntro;
    }

    public UserInfoResult getCreator() {
        return creator;
    }

    public void setCreator(UserInfoResult creator) {
        this.creator = creator;
    }

    public Date getFandomCreateTime() {
        return fandomCreateTime;
    }

    public void setFandomCreateTime(Date fandomCreateTime) {
        this.fandomCreateTime = fandomCreateTime;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

    public String getFandomAdminSn() {
        return fandomAdminSn;
    }

    public void setFandomAdminSn(String fandomAdminSn) {
        this.fandomAdminSn = fandomAdminSn;
    }

    public String getFandomCreatorSn() {
        return fandomCreatorSn;
    }

    public void setFandomCreatorSn(String fandomCreatorSn) {
        this.fandomCreatorSn = fandomCreatorSn;
    }

    public String getFandomParentName() {
        return fandomParentName;
    }

    public void setFandomParentName(String fandomParentName) {
        this.fandomParentName = fandomParentName;
    }

    public List<FandomTagResult> getFandomTagResultList() {
        return fandomTagResultList;
    }

    public void setFandomTagResultList(List<FandomTagResult> fandomTagResultList) {
        this.fandomTagResultList = fandomTagResultList;
    }

    public Map<String, Object> getFandomParentInfo() {
        return fandomParentInfo;
    }

    public void setFandomParentInfo(Map<String, Object> fandomParentInfo) {
        this.fandomParentInfo = fandomParentInfo;
    }
}
