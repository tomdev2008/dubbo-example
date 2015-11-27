package com.fansz.members.api.utils;


/**
 * Created by root on 15-10-12.
 */
public class Constants {

    public static String MOBILE = "mobile";

    public static String USER_ID = "id";

    public static String FRIENDS_USER_AGREE = "userIdAgree";

    public static String FRIENDS_STATUS = "status";

    public static String FRIENDS_USER_ADD = "userIdAdd";

    public static String FRIENDS_STATUS_DELETE = "0";

    public static String FRIENDS_STATUS_FOLLOW = "1";

    public static String FRIENDS_STATUS_FRIEND = "2";

    public static String FRIENDS_STATUS_FOLLOW_ME = "3";

    public static String FRIENDS_STATUS_MYSELF = "4";

    public static String FRIENDS_STATUS_NOT_KNOW = "5";

    public static String FRIENDS_CREATE_TIME = "createTime";

    public static int PAGE_SIZE_DEFAULT = 20;

    public static int PAGE_BEGIN_DEFAULT = 0;

    public static int PAGE_BEGIN_OVER = 100;

    public static int RECOMMEND_COUNT = 3;

    public static String IMAGE_ID = System.getProperty("user.dir") + "/images/portrait/{id}";

    public static int PICTURE_CACHE_TIME = 31536000;

}
