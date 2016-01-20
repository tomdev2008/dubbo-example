package com.fansz.auth.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by allan on 15/12/3.
 */
public class VerifyCodeParam implements Serializable {

    private static final long serialVersionUID = 6875364016096654107L;

    //@Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")
    @NotBlank
    @Digits(integer = 11, fraction = 0)
    @Size(min = 1, max = 20)
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
