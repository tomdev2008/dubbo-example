package com.fansz.members.model.param;

import com.fansz.appservice.utils.RegularPattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by xuzhen on 15/11/3.
 */

@Data
public class ResetPasswordParam {

    @NotEmpty(message = "error.mobile.empty")
    @Length(max = 20, message = "error.mobile.over")
    private String mobile;

    @NotEmpty(message = "error.password.empty")
    @Length(max = 20, message = "error.password.over")
    @Pattern(regexp = RegularPattern.PATTERN_PASSWORD, message = "error.password.wrongformat")
    private String password;

    @NotEmpty(message = "error.identifyCode.empty")
    private String identifyCode;

}
