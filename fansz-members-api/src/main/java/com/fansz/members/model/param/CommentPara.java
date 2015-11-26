package com.fansz.members.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by root on 15-11-4.
 */
@Data
@NoArgsConstructor
public class CommentPara {

    @NotEmpty(message = "error.content.empty")
    @Length(max = 500, message = "error.content.over")
    private String content;

    @NotEmpty(message = "error.postId.empty")
    private String postId;

}
