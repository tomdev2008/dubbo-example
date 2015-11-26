package com.fansz.members.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by root on 15-11-9.
 */
@Data
public class CriteriaParam {

    @NotEmpty(message = "error.criteria.empty")
    private String criteria;
}
