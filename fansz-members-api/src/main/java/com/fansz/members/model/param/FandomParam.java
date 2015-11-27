package com.fansz.members.model.param;

import org.springframework.util.Assert;

import java.io.InputStream;

/**
 * Created by root on 15-11-4.
 */
public class FandomParam {

    private String title;

    private String categoryId;

    private String description;

    private InputStream avatar;

    private InputStream backImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(InputStream avatar) {
        this.avatar = avatar;
    }

    public InputStream getBackImage() {
        return backImage;
    }

    public void setBackImage(InputStream backImage) {
        this.backImage = backImage;
    }
}
