package com.fansz.token.utils;

/**
 * Created by wukai on 16/1/20.
 */
public final class IMUtils {
    public static final String APP_ID = "0101201601200001";

    public static String getUserId(String memberSn, String appKey){
        return memberSn + "@" + appKey;
    }

    public static String getAppId() {
        return APP_ID;
    }
}
