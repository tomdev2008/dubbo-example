package com.fansz.redis;

import com.fansz.redis.model.CountListResult;

import java.util.Set;

/**
 * Created by allan on 16/1/8.
 */
public interface RelationTemplate {

    Long getMemberCount(String key);

    Set<String> listByPosition(String key, long start, long end);

    /**
     * 添加好友
     *
     * @param mySn     添加人
     * @param friendSn 要添加的朋友
     * @return
     */
    boolean addFriend(final String mySn, final String friendSn);

    boolean addAsSpecial(final String mySn, final String friendSn);

    boolean removeSpecial(final String mySn, final String friendSn);

    boolean agreeFriend(final String mySn, final String friendSn);

    String getRelation(final String currentSn, final String sn);

    boolean addFriendRemark(String mySn, String friendSn, String remark);

    /**
     * 查询用户主动添加的请求
     *
     * @param mySn
     * @return
     */
    CountListResult<String> listMySendRequest(final String mySn, final long offset, final long limit);

    /**
     * 查询用户接收到的请求
     *
     * @param mySn
     * @return
     */
    CountListResult<String> listMyReceiveRequest(final String mySn, final long offset, final long limit);

    /**
     * 查询好友
     *
     * @param mySn
     * @param offset
     * @param limit
     * @return
     */
    CountListResult<String> listFriend(final String mySn, final long offset, final long limit);

    /**
     * 查询特殊好友
     *
     * @param mySn
     * @param offset
     * @param limit
     * @return
     */
    CountListResult<String> listSpecialFriend(final String mySn, final long offset, final long limit);


}