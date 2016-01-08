package com.fansz.relations.service.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.constant.RelationShip;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.utils.RedisKeyUtils;
import com.fansz.db.model.FriendInfo;
import com.fansz.db.repository.UserDAO;
import com.fansz.db.repository.UserRelationDAO;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import com.fansz.relations.model.*;
import com.fansz.relations.service.RelationShipService;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by allan on 16/1/7.
 */
@Component("relationShipRedisService")
public class RelationShipRedisServiceImpl implements RelationShipService {

    @Resource(name = "jedisTemplate")
    private JedisTemplate jedisTemplate;

    @Resource(name = "userRelationDAO")
    private UserRelationDAO userRelationDAO;

    @Override
    public QueryResult<FriendInfoResult> getFriends(final FriendsQueryParam friendsQueryParam, final boolean isSpecial) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();
        return jedisTemplate.execute(new JedisCallback<QueryResult<FriendInfoResult>>() {
            @Override
            public QueryResult<FriendInfoResult> doInRedis(Jedis jedis) throws Exception {
                String mySn = friendsQueryParam.getCurrentSn();
                String key = isSpecial ? RedisKeyUtils.getSpeicalFriendKey(mySn) : RedisKeyUtils.getFriendKey(mySn);
                long totalSize = jedis.zcard(key);
                if (totalSize == 0 || totalSize < offset) {
                    return new QueryResult<FriendInfoResult>(new ArrayList<FriendInfoResult>(), totalSize);
                }

                Set<String> result = jedis.zrange(key, offset, offset + limit);
                Pipeline pipe = jedis.pipelined();
                for (String sn : result) {
                    pipe.hgetAll("user:{" + sn + "}");
                }
                List<Object> requestList = pipe.syncAndReturnAll();
                List<FriendInfoResult> dataList = new ArrayList<>();
                for (Object record : requestList) {
                    Map<String, Object> m = (Map<String, Object>) record;
                    FriendInfoResult friendInfoResult = JsonHelper.copyAs(m, FriendInfoResult.class);
                    if (isSpecial) {
                        friendInfoResult.setRelationship(RelationShip.SPECIAL_FRIEND.getCode());
                    } else {
                        friendInfoResult.setRelationship(getRelation(mySn, friendInfoResult.getSn()));
                    }
                    dataList.add(friendInfoResult);
                }
                return new QueryResult<>(dataList, totalSize);
            }
        });
    }

    /**
     * 发出添加好友请求
     *
     * @param addFriendParam
     * @return
     */
    @Override
    public boolean addFriendRequest(final AddFriendParam addFriendParam) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                boolean exist = jedis.sismember(RedisKeyUtils.getFriendKey(addFriendParam.getCurrentSn()), addFriendParam.getFriendMemberSn());
                if (exist) {
                    throw new ApplicationException(ErrorCode.RELATION_IS_FRIEND);
                }
                Pipeline pipe = jedis.pipelined();
                long now = System.currentTimeMillis();
                pipe.zadd(RedisKeyUtils.getMyRequestKey(addFriendParam.getCurrentSn()), now, addFriendParam.getFriendMemberSn());
                pipe.zadd(RedisKeyUtils.getMyRequestedKey(addFriendParam.getFriendMemberSn()), now, addFriendParam.getCurrentSn());
                pipe.sync();
                return true;
            }
        });
    }

    /**
     * 添加或取消特殊关注
     *
     * @param addFriendParam
     * @param add
     * @return
     */
    @Override
    public boolean dealSpecialFriend(final AddFriendParam addFriendParam, final boolean add) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                if (add) {//添加特殊关注,要求是朋友且不能是特殊关注
                    boolean isFriend = jedis.sismember(RedisKeyUtils.getFriendKey(addFriendParam.getCurrentSn()), addFriendParam.getFriendMemberSn());
                    boolean isSfriend = jedis.sismember(RedisKeyUtils.getSpeicalFriendKey(addFriendParam.getCurrentSn()), addFriendParam.getFriendMemberSn());
                    if (!isFriend || isSfriend) {
                        throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_ADD);
                    }
                    jedis.zadd(RedisKeyUtils.SP_FRIEND_PREFIX + addFriendParam.getCurrentSn(), System.currentTimeMillis(), addFriendParam.getFriendMemberSn());
                } else {//取消特殊关注,要求已经是特殊好友
                    boolean exist = jedis.sismember(RedisKeyUtils.getSpeicalFriendKey(addFriendParam.getCurrentSn()), addFriendParam.getFriendMemberSn());
                    if (!exist) {
                        throw new ApplicationException(ErrorCode.RELATION_SPECIAL_NO_DEL);
                    }
                    jedis.zrem(RedisKeyUtils.getSpeicalFriendKey(addFriendParam.getCurrentSn()), addFriendParam.getFriendMemberSn());
                }
                return true;
            }
        });
    }

    /**
     * 同意好友请求
     *
     * @param opRequestParam
     * @param agree
     * @return
     */
    @Override
    public boolean dealFriendRequest(final OpRequestParam opRequestParam, boolean agree) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                //检查对方是否在我接收到的好友请求列表中,如果不在,则返回错误消息
                boolean exist = jedis.sismember(RedisKeyUtils.MY_REQUESTED + opRequestParam.getCurrentSn(), opRequestParam.getFriendMemberSn());
                if (!exist) {
                    throw new ApplicationException(ErrorCode.RELATION_FRIEND_NO_EXISTS);
                }
                //如果在列表中,则从请求sorted set中删除请求,并添加到朋友sorted set中
                Pipeline pipe = jedis.pipelined();
                pipe.zadd(RedisKeyUtils.getFriendKey(opRequestParam.getCurrentSn()), System.currentTimeMillis(), opRequestParam.getFriendMemberSn());
                pipe.zadd(RedisKeyUtils.getMyRequestedKey(opRequestParam.getFriendMemberSn()), System.currentTimeMillis(), opRequestParam.getCurrentSn());
                pipe.zrem(RedisKeyUtils.getMyRequestedKey(opRequestParam.getCurrentSn()), opRequestParam.getFriendMemberSn());
                pipe.sync();
                return true;
            }
        });
    }

    /**
     * 分页查询我接收到的好友请求列表,当前为好友\特殊好友关系的数据也返回
     *
     * @param friendsQueryParam
     * @return
     */
    @Override
    public QueryResult<FriendInfoResult> listAddMeRequest(final FriendsQueryParam friendsQueryParam) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();
        return jedisTemplate.execute(new JedisCallback<QueryResult<FriendInfoResult>>() {
            @Override
            public QueryResult<FriendInfoResult> doInRedis(Jedis jedis) throws Exception {
                String mySn = friendsQueryParam.getCurrentSn();
                String unionKey = RedisKeyUtils.getUnionKey(mySn);
                long totalSize = jedis.zunionstore(unionKey, RedisKeyUtils.getMyRequestedKey(mySn), RedisKeyUtils.getFriendKey(mySn), RedisKeyUtils.getSpeicalFriendKey(mySn));
                if (totalSize == 0 || totalSize < offset) {
                    return new QueryResult<FriendInfoResult>(new ArrayList<FriendInfoResult>(), totalSize);
                }
                Set<String> result = jedis.zrange(unionKey, offset, offset + limit);
                Pipeline pipe = jedis.pipelined();
                for (String sn : result) {
                    pipe.hgetAll("user:{" + sn + "}");
                }
                List<Object> requestList = pipe.syncAndReturnAll();
                jedis.del(unionKey);
                List<FriendInfoResult> dataList = new ArrayList<FriendInfoResult>();
                for (Object record : requestList) {
                    Map<String, Object> m = (Map<String, Object>) record;
                    FriendInfoResult friendInfoResult = JsonHelper.copyAs(m, FriendInfoResult.class);
                    dataList.add(friendInfoResult);
                }
                return new QueryResult<>(dataList, totalSize);
            }
        });
    }

    /**
     * 分页查询我主动添加的好友请求,当前为好友\特殊好友关系的数据也返回
     *
     * @param friendsQueryParam
     * @return
     */
    @Override
    public QueryResult<FriendInfoResult> listMySendRequest(final FriendsQueryParam friendsQueryParam) {
        final int offset = (friendsQueryParam.getPageNum() - 1) * friendsQueryParam.getPageSize();
        final int limit = friendsQueryParam.getPageSize();
        return jedisTemplate.execute(new JedisCallback<QueryResult<FriendInfoResult>>() {
            @Override
            public QueryResult<FriendInfoResult> doInRedis(Jedis jedis) throws Exception {
                String mySn = friendsQueryParam.getCurrentSn();
                String unionKey = RedisKeyUtils.getUnionKey(mySn);
                long totalSize = jedis.zunionstore(unionKey, RedisKeyUtils.getMyRequestKey(mySn), RedisKeyUtils.getFriendKey(mySn), RedisKeyUtils.getSpeicalFriendKey(mySn));
                if (totalSize == 0 || totalSize < offset) {
                    return new QueryResult<FriendInfoResult>(new ArrayList<FriendInfoResult>(), totalSize);
                }
                Set<String> result = jedis.zrange(unionKey, offset, offset + limit);
                Pipeline pipe = jedis.pipelined();
                for (String sn : result) {
                    pipe.hgetAll("user:{" + sn + "}");
                }
                List<Object> requestList = pipe.syncAndReturnAll();
                jedis.del(unionKey);
                List<FriendInfoResult> dataList = new ArrayList<FriendInfoResult>();
                for (Object record : requestList) {
                    Map<String, Object> m = (Map<String, Object>) record;
                    FriendInfoResult friendInfoResult = JsonHelper.copyAs(m, FriendInfoResult.class);
                    dataList.add(friendInfoResult);
                }
                return new QueryResult<>(dataList, totalSize);
            }
        });
    }

    @Override
    public QueryResult<FriendInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam) {
        Page p = new Page();
        p.setPage(contactQueryParam.getPageNum());
        p.setPageSize(contactQueryParam.getPageSize());
        QueryResult<FriendInfo> friendList = userRelationDAO.findUserByMobiles(p, contactQueryParam.getMobileList());
        return renderResult(contactQueryParam.getCurrentSn(), friendList);
    }

    private QueryResult<FriendInfoResult> renderResult(String currentSn, QueryResult<FriendInfo> userList) {
        if (CollectionTools.isNullOrEmpty(userList.getResultlist())) {
            return new QueryResult<>(new ArrayList<FriendInfoResult>(), 0);
        }
        List<FriendInfoResult> friendList = new ArrayList<FriendInfoResult>();
        for (FriendInfo info : userList.getResultlist()) {
            FriendInfoResult friendInfoResult = BeanTools.copyAs(info, FriendInfoResult.class);
            friendInfoResult.setRelationship(getRelation(currentSn, friendInfoResult.getSn()));
            friendList.add(friendInfoResult);
        }
        return new QueryResult<>(friendList, userList.getTotalrecord());
    }

    /**
     * 获取用户间的关系
     *
     * @param currentSn 当前用户
     * @param sn        手机联系人员用户
     * @return
     */
    private String getRelation(final String currentSn, final String sn) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                if (jedis.sismember(RedisKeyUtils.getSpeicalFriendKey(currentSn), sn)) {
                    return RelationShip.SPECIAL_FRIEND.getCode();
                } else if (jedis.sismember(RedisKeyUtils.getFriendKey(currentSn), sn)) {
                    return RelationShip.FRIEND.getCode();
                }
                if (jedis.sismember(RedisKeyUtils.getMyRequestKey(currentSn), sn)) {
                    return RelationShip.TO_ADD.getCode();
                } else if (jedis.sismember(RedisKeyUtils.getMyRequestedKey(currentSn), sn)) {
                    return RelationShip.BE_ADDED.getCode();
                } else {
                    return null;
                }
            }
        });
    }
}
