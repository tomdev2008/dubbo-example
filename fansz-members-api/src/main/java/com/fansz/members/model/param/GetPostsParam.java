package com.fansz.members.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by root on 15-11-19.
 */
@Data
public class GetPostsParam {

    @NotEmpty(message = "error.fandom.empty")
    private String id;

    private String kind;
}
