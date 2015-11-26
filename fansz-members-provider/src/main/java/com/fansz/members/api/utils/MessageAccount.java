package com.fansz.members.api.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by root on 15-10-21.
 */
@ConfigurationProperties(prefix = "meassge")
public class MessageAccount {

    public static String url = "http://222.73.117.158/msg/HttpBatchSendSM?";// 应用地址

    public static String account = "vip_hlj";// 账号

    public static String pwd = "Tch123456";// 密码

    public static String needstatus = "1";

    public static int count = 3;

    public static long passwordPeriod = 60000;

    public static long identityCodePeriod = 60000;

    public static String passwordMsg = "动态验证码为{code}，该验证码用于Fansz登录，{time}分钟内有效。";

    public static String identityCodeMsg = "动态验证码为{code}，该验证码用于Fansz的注册，{time}分钟内有效。";

    public static String passwordTime = "1";

    public static String identityCodeTime = "1";

    public static int maxCount = 500;
}
