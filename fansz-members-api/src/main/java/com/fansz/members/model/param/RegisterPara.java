package com.fansz.members.model.param;

/**
 * Created by root on 15-11-3.
 */

import com.fansz.appservice.utils.RegularPattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
public class RegisterPara {

    @NotEmpty(message = "error.loginName.empty")
    @Length(max = 20, message = "error.loginName.over")
    private String loginName;


    @NotEmpty(message = "error.password.empty")
    @Length(max = 20, message = "error.password.over")
    @Pattern(regexp = RegularPattern.PATTERN_PASSWORD, message = "error.password.wrongformat")
    private String password;

    @Length(max = 20, message = "error.mobile.over")
    private String mobile;

    @Length(max = 50, message = "error.email.over")
    private String email;

    @NotEmpty(message = "error.identifyCode.empty")
    private String identifyCode;
}
