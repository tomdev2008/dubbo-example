package com.fansz.redis.utils;

import com.fansz.pub.utils.UUIDTools;

/**
 * Created by allan on 16/1/7.
 */
public final class RedisKeyUtils {
    //user
    private final static String USER_PREFIX = "u:{";//用户信息

    private final static String IDX_USER_MOBILE_PREFIX = "u:mob:";//用户信息

    private final static String IDX_USER_ACCOUNT_PREFIX = "u:ac:";//用户信息

    private final static String IDX_USER_NICK_PREFIX = "u:nick:";//用户信息

    private final static String SEQ_USER="seq:user";

    //session

    private final static String SESSION_PREFIX = "session:";

    private final static String REFRESH_TOKEN_PREFIX = "token:refresh:";

    private final static String VERIFY_KEY_PREFIX = "verify:";


    //relation相关
    private final static String FRIEND_PREFIX = "friend:{";//我的好友

    private final static String SP_FRIEND_PREFIX = "sfriend:{";//我的特别好友

    private final static String MY_REQUEST = "request:{";//我发出的好友请求

    private final static String MY_REQUESTED = "requested:{";//我接收到的好友请求

    private final static String HASH_TAG_SUFFIX = "}";//HASH TAG后缀

    private final static String TMP_PREFIX = "tmp:";//临时key

    private final static String FRIEND_REMARK_PREFIX = "friend_remark:{";

    /**
     * 我的好友
     *
     * @param sn
     * @return
     */
    public static String getFriendKey(String sn) {
        return FRIEND_PREFIX + sn + HASH_TAG_SUFFIX;
    }

    /**
     * 我发出的好友请求
     *
     * @param sn
     * @return
     */
    public static String getMyRequestKey(String sn) {
        return MY_REQUEST + sn + HASH_TAG_SUFFIX;
    }

    /**
     * 我接收到的好友请求
     *
     * @param sn
     * @return
     */
    public static String getMyRequestedKey(String sn) {
        return MY_REQUESTED + sn + HASH_TAG_SUFFIX;
    }

    /**
     * 我的特殊好友
     *
     * @param sn
     * @return
     */
    public static String getSpeicalFriendKey(String sn) {
        return SP_FRIEND_PREFIX + sn + HASH_TAG_SUFFIX;
    }

    /**
     * 临时key
     *
     * @param key
     * @return
     */
    public static String getUnionKey(String key) {
        return TMP_PREFIX + UUIDTools.generate() + key ;
    }

    /**
     * 好友备注
     *
     * @param sn
     * @return
     */
    public static String getFriendRemarkKey(String sn) {
        return FRIEND_REMARK_PREFIX + sn + HASH_TAG_SUFFIX;
    }

    /**
     * 用户Key
     *
     * @param sn
     * @return
     */
    public static String getUserKey(String sn) {
        return USER_PREFIX + sn + HASH_TAG_SUFFIX;
    }

    public static String getSessionKey(String accessToken) {
        return SESSION_PREFIX + accessToken;
    }

    public static String getRefreshKey(String refreshToken) {
        return REFRESH_TOKEN_PREFIX + refreshToken;
    }

    public static String getVerifyKey(String mobile) {
        return VERIFY_KEY_PREFIX + mobile;
    }

    public static String getMobileIdxKey(String mobile) {
        final int shard=mobile.hashCode()%1000;
        return IDX_USER_MOBILE_PREFIX + shard;
    }
    public static String getAccountIdxKey(String account) {
        final int shard=account.hashCode()%1000;
        return IDX_USER_ACCOUNT_PREFIX + shard;
    }

    public static String getNickIdxKey(String nickname) {
        final int shard=nickname.hashCode()%1000;
        return IDX_USER_NICK_PREFIX + shard;
    }
    public static String getUserSequence(){
        return SEQ_USER;
    }
}

