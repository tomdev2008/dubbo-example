package com.fansz.constant;

public class AppConstants {

    // 配置项相关常量定义
    public static final String SERVER_NAME = "server.name";

    public static final String SERVER_PORT = "server.port";

    public static final String CONSOLE_PORT = "console.port";

    // 用户信息数据库

    public static final String SEQ_UID = "seq_uid";// 用户UID sequence产生器

    /**
     * {appKey}+"user",存储格式为Hash,key为UID,value为用户信息
     */
    public static final String APP_USER = "%s:user";

    /**
     * 物理地址和UID的映射,存储格式为Hash,物理地址定义：IOS:deviceToken,Android:IMEI+SerialNum+Mac+appKey
     */
    public static final String USERS_PHYSICAL_ADDRESS = "user:physical";

    /**
     * "tags"+{appKey}+{tag}，存储格式为set,对于Android APP,存储的UID，对于IOS，存储的是deviceToken
     */
    public static final String USERS_TAG = "tags:%s:%s";

    /**
     * "alias"+{appKey},存储格式为Hash，KEY为别名，VALUE为Andoird的UID，IOS的deviceToken
     */
    public static final String USERS_ALIAS = "alias:%s";

    /**
     * 用户状态，存储类型为bit,uid为index,在线，对应的位为1，离线为0
     */
    public static final String USERS_STATUS = "user:status";

    /**
     * IOS证书配置，存储类型为Hash,KEY为appKey，value为配置信息
     */
    public static final String CERT_CONFIG = "cert:ios";

    // 消息数据库
    public static final String MESSAGE_TODO = "%s:message:todo";// List

    public static final String MESSAGE_DONE = "%s:message:done";// List

    public static final String MESSAGE_QUEUE_TODO = "%s:queue:todo";// SET,待发送的队列

    public static final String MESSAGE_QUEUE_DONE = "%s:queue:done";// SET,客户端已经接收到的队列

    public static final String MESSAGE_QUEUE_CLICK = "%s:queue:click";// SET,客户端已经接收到的队列

    public static final String NULL = "nil";

    public static final String CODE_SUCCESS = "1";

    public static final String CODE_FAIL = "0";

    public static final String CODE_REGISTED_ERROR_UID = "2";// 用户已注册

    public final static String PLATFORM_ANDROID = "android";

    public final static String PLATFORM_IOS = "ios";

    /**
     * Redis DB ID
     */
    public final static int DB_USER = 0;// 存放用户基本信息已经sequence序列

    public final static int DB_ANDROID = 1;// 存放Android推送消息

    public final static int DB_IOS = 2;// 存放IOS推送消息
}
