package com.fansz.members.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created by root on 15-11-3.
 */
@Data
public class ModifyProfilePara {

    @Length(max = 20, message = "error.nickName.over")
    private String nickName;

    @Length(max = 50, message = "error.birthday.over")
    private String birthday;

    @Length(max = 10, message = "error.gender.over")
    private String gender;
}
