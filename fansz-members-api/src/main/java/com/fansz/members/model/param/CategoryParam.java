package com.fansz.members.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by root on 15-11-13.
 */
@Data
public class CategoryParam {

    @NotEmpty(message = "error.parent.id")
    // 父类别
    private String parentId;

    // 标题
    private String title;

    // 描述内容
    private String content;

    // 头像
//    private FormDataBodyPart avatar;

}
