package com.fansz.members.model;

import com.fansz.appservice.resource.param.CategoryParam;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by root on 15-11-11.
 * fandom类别
 */
@Data
@Document(collection = "category")
@NoArgsConstructor
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

    public Category(CategoryParam categoryParam) {
        this.id = ShortUUIDGenerator.generate();
        this.title = categoryParam.getTitle();
        this.content = categoryParam.getContent();
    }
}
