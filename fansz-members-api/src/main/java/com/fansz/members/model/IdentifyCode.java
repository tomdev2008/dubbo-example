package com.fansz.members.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by root on 15-10-20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="identifyCode")
public class IdentifyCode {

    private String mobile;

    private String identifyCode;

    private Date expiredTime;

    private int sendCount;

}
