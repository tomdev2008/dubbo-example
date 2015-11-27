package com.fansz.members.model;

import java.util.List;

/**
 * Created by root on 15-11-12.
 */
public class SubCategory {

    // 编号
    private String id;

    // 父id
    private String parentId;

    // 标题
    private String title;

    // 描述内容
    private String content;

    // 头像图片
    private String avatar;

    // 该子类别里面的fandom
    private List<Fandom> fandoms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public List<Fandom> getFandoms() {
        return fandoms;
    }

    public void setFandoms(List<Fandom> fandoms) {
        this.fandoms = fandoms;
    }
}
