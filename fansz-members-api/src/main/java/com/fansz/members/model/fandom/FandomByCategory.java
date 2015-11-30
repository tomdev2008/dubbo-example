package com.fansz.members.model.fandom;

/**
 * Created by root on 15-11-27.
 */
public class FandomByCategory {

    private Integer userId;

    private Long categoryId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
