package com.fansz.members.model.friendship;

import com.fansz.members.model.user.UserInfoResult;

import java.io.Serializable;

/**
 * 用户关注的fandom信息
 */
public class FocusedFandomResult implements Serializable {
    private Long id;

    private String title;

    private String description;

    private String avatarUr;

    private int categoryId;

    private UserInfoResult owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUr() {
        return avatarUr;
    }

    public void setAvatarUr(String avatarUr) {
        this.avatarUr = avatarUr;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public UserInfoResult getOwner() {
        return owner;
    }

    public void setOwner(UserInfoResult owner) {
        this.owner = owner;
    }
}
