package com.fansz.members.model.param;

import com.fansz.appservice.utils.RegularPattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by root on 15-11-3.
 */
@Data
public class ChangePasswordPara {

    @NotEmpty(message = "error.userId.empty")
    @Length(max = 50, message = "error.userId.over")
    private String userId;

    @Pattern(regexp = RegularPattern.PATTERN_PASSWORD, message = "error.password.wrongformat")
    @NotEmpty(message = "error.passwordOld.empty")
    @Length(max = 20, message = "error.password.over")
    private String passwordOld;

    @Pattern(regexp = RegularPattern.PATTERN_PASSWORD, message = "error.password.wrongformat")
    @NotEmpty(message = "error.passwordNew.empty")
    @Length(max = 20, message = "error.password.over")
    private String passwordNew;
}
