package com.fansz.members.model;


import java.util.List;

/**
 * Created by root on 15-11-11.
 * fandom类别
 */
public class Category {

    // 编号
    private String id;

    // 标题
    private String title;

    // 描述内容
    private String content;

    // 头像图片
    private String avatar;

    // 子类别
    private List<SubCategory> subCategories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
