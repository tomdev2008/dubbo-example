package com.fansz.members.model.param;

/**
 * Created by root on 15-11-13.
 */

public class CategoryParam {

    // 父类别
    private String parentId;

    // 标题
    private String title;

    // 描述内容
    private String content;

    // 头像
//    private FormDataBodyPart avatar;


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
}
