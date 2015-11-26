package com.fansz.members.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by root on 15-11-20.
 */
@Data
public class FandomFollowers extends PagePara{

    @NotEmpty(message = "error.fandom.empty")
    private String id;

    private int pageStart;

    private int count;
}
