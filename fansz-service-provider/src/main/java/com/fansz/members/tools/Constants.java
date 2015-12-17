package com.fansz.members.tools;


/**
 *
 * TODO:后续需要进行重构,改为枚举类型
 */
public class Constants {
    public final static String SUCCESS = "0";

    public final static String FAIL = "1";

    public final static String VERIFY_KEY = "verify:";

    public final static String SYSTEM_ERROR = "10001";//系统错误

    public final static String PARAMETERS_ERROR = "10008";//参数错误

    public final static String VERIFY_ERROR = "20000";//验证码错误

    public final static String USER_NOT_FOUND = "20001";//用户不存在

    public final static String MOBILE_NOT_FOUND = "20002";//手机号码不存在

    public final static String MOBILE_IS_USED = "20003";//手机号码已经被使用

    public final static String PASSWORD_WRONG = "20004";//密码错误

    public final static String USER_EXISTS = "20005";//用户已存在

    public final static String INTERVAL_IS_TOO_SHORT = "20006";//发送间隔过短

    public final static String TOKEN_INVALID="20007";//Token无效

    public final static String RELATION_FRIEND_NO_EXISTS="20200";//无法添加好友

    public final static String RELATION_IS_FRIEND="20201";//已经是好友

    public final static String RELATION_SPECIAL_NO_ADD="20202";//无法添加特殊好友

    public final static String RELATION_SPECIAL_NO_DEL="20203";//无法取消特殊好友

    public final static String RELATION_IS_NOT_FRIEND="20204";//不是好友,无法添加特殊好友

    public final static String RELATION_IS_IN_FANDOM="20205";//用户已经关注fandom

    public final static String RELATION_IS_NOT_IN_FANDOM="20206";//用户未关注fandom

    public final static String RELATION_IS_SPACIEL_FANDOM="20207";//用户已经特殊关注fandom

    public final static String RELATION_IS_NOT_SPACIEL_FANDOM="20208";//用户未特殊关注fandom

    public final static String POST_NOT_EXISTS="20301";//POST不存在

    public final static String POST_NOT_ALLOW_DEL="20302";//无法删除他人帖子

    public final static String NICK_NAME_REPATEDD="20401";//nickname重复

    public final static String COMMENT_NOT_EXISTS="20501";//评论不存在

    public final static String USER_STATUS_OK = "1";

    public final static String RELATION_FRIEND="10";
}