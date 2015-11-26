package com.fansz.members.model.param;

import com.fansz.appservice.utils.RegularPattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * Created by root on 15-11-4.
 */
@Data
public class CommentPagePara {

    @NotEmpty(message = "error.postId.empty")
    private String postId;

    @Pattern(regexp = RegularPattern.PATTERN_POSITIVE_INTERGE, message = "error.int.pageStart")
    private int pageStart;

    @Pattern(regexp = RegularPattern.PATTERN_POSITIVE_INTERGE, message = "error.int.count")
    @Range(max = 100, message = "error.over.count")
    private int count;
}
