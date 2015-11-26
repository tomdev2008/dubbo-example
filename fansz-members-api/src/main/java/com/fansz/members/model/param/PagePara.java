package com.fansz.members.model.param;

import com.fansz.appservice.utils.RegularPattern;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * Created by root on 15-11-10.
 */
@Data
public class PagePara {

    @Pattern(regexp = RegularPattern.PATTERN_POSITIVE_INTERGE, message = "error.int.pageStart")
    private int pageStart;

    @Pattern(regexp = RegularPattern.PATTERN_POSITIVE_INTERGE, message = "error.int.count")
    private int count;
}
