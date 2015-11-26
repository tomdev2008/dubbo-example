package com.fansz.members.model;


import lombok.Data;

import java.util.List;

/**
 * Created by root on 15-11-11.
 * fandom类别
 */
@Data
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
}
