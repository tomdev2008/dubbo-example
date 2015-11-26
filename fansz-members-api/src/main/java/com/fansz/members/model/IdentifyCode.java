package com.fansz.members.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by root on 15-10-20.
 */
@Data
public class IdentifyCode {

    private String mobile;

    private String identifyCode;

    private Date expiredTime;

    private int sendCount;

}
