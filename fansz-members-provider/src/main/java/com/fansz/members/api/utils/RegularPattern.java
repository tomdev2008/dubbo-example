package com.fansz.members.api.utils;

/**
 * 常用正则表达式
 *
 * Created by xuzhen on 15/6/2.
 */
public final class RegularPattern {

    //校验码
    public static final String PATTERN_VALIDCODE = "\\d{" + IdentifyCodeGenerator.CODE_LENGTH + "}";

    //手机号
    public static final String PATTERN_MOBILE = "^\\+?(\\d{1,4})?((13[0-9])|(14[57])|(15[^4\\D])|(17[0678])|(18\\d))\\d{8}$";
    //密码
    public static final String PATTERN_PASSWORD = "^[^%\\^&\\*\\+=;\"/\\?|\\\\{}\\[\\]]{6,16}$";
    //日期格式（yyyy-MM-dd)
    public static final String PATTERN_DATE = "^\\d{4}-((0[1-9])|(1[012]))-((0[1-9])|([12][0-9])|(3[01]))$";
    //可用字符
    public static final String PATTERN_VALIDCHARS = "^[^%\\^&\\*\\+=;\"/\\?|\\\\{}\\[\\]]*$";
    //年月
    public static final String PATTERN_YEARMONTH = "\\d{4}(0[1-9]|1[0-2])";

    public static final String PATTERN_POSITIVE_INTERGE = "^\\+?[1-9][0-9]*$";
}
