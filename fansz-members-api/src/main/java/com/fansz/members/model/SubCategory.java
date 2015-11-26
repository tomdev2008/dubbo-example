package com.fansz.members.model;

import com.fansz.appservice.resource.param.CategoryParam;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by root on 15-11-12.
 */
@Data
@NoArgsConstructor
@Document(collection = "subCategory")
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
}
