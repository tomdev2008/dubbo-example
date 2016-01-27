package com.fansz.redis.impl;

import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.constant.RelationShip;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.model.CountListResult;
import com.fansz.redis.support.JedisCallback;
import com.fansz.redis.utils.RedisKeyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by allan on 16/1/8.
 */
public class RelationTemplateImpl implements RelationTemplate {

    private JedisTemplate jedisTemplate;

    @Override
    public Long getMemberCount(final String key) {
        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                return jedis.zcard(key);
            }
        });
    }

    @Override
    public Set<String> listByPosition(final String key, final long start, final long end) {
        return jedisTemplate.execute(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(Jedis jedis) throws Exception {
                return jedis.zrange(key, start, end);
            }
        });
    }

    @Override
    public boolean addFriend(final String mySn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Pipeline pipe = jedis.pipelined();
                long now = System.currentTimeMillis();
                pipe.zadd(RedisKeyUtils.getMyRequestKey(mySn), now, friendSn);
                pipe.zadd(RedisKeyUtils.getMyRequestedKey(friendSn), now, mySn);
                pipe.sync();
                return true;
            }
        });
    }

    /**
     * 特殊关注好友,不需要好友同意
     *
     * @return
     */
    @Override
    public boolean addAsSpecial(final String mySn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.zadd(RedisKeyUtils.getSpeicalFriendKey(mySn), System.currentTimeMillis(), friendSn);
                return true;
            }
        });
    }

    /**
     * 取消特殊关注好友
     *
     * @return
     */
    @Override
    public boolean removeSpecial(final String mySn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                jedis.zrem(RedisKeyUtils.getSpeicalFriendKey(mySn), friendSn);
                return true;
            }
        });
    }

    @Override
    public boolean agreeFriend(final String mySn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                //如果在列表中,则从请求sorted set中删除请求,并添加到朋友sorted set中
                long now = System.currentTimeMillis();
                Pipeline pipe = jedis.pipelined();
                pipe.zadd(RedisKeyUtils.getFriendKey(mySn), now, friendSn);
                pipe.zadd(RedisKeyUtils.getFriendKey(friendSn), now, mySn);
                pipe.zrem(RedisKeyUtils.getMyRequestedKey(mySn), friendSn);
                pipe.zrem(RedisKeyUtils.getMyRequestKey(friendSn), mySn);
                pipe.sync();
                return true;
            }
        });
    }

    @Override
    public boolean addFriendRemark(final String mySn, final String friendSn, final String remark) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                //检查对方是否在我的好友列表中,如果不是,返回错误
                String relationship = getRelation(mySn, friendSn);
                if (!RelationShip.FRIEND.getCode().equals(relationship) && !RelationShip.SPECIAL_FRIEND.getCode().equals(relationship)) {
                    throw new ApplicationException(ErrorCode.RELATION_FRIEND_NO_EXISTS);
                }
                jedis.hset(RedisKeyUtils.getFriendRemarkKey(mySn), friendSn, remark);
                return true;
            }
        });
    }

    @Override
    public boolean removeFriendRemark(final String mySn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                if (jedis.hexists(RedisKeyUtils.getFriendRemarkKey(mySn), friendSn)) {
                    jedis.hdel(RedisKeyUtils.getFriendRemarkKey(mySn), friendSn);
                }
                return true;
            }
        });
    }

    @Override
    public CountListResult<String> listMySendRequest(final String mySn, final long offset, final long limit) {
        final String unionKey = RedisKeyUtils.getUnionKey(mySn);
        return jedisTemplate.execute(new JedisCallback<CountListResult<String>>() {
            @Override
            public CountListResult<String> doInRedis(Jedis jedis) throws Exception {
                try {
                    long totalSize = jedis.zunionstore(unionKey, RedisKeyUtils.getMyRequestKey(mySn), RedisKeyUtils.getFriendKey(mySn), RedisKeyUtils.getSpeicalFriendKey(mySn));
                    if (totalSize == 0 || totalSize < offset) {
                        return null;
                    }
                    Set<String> result = jedis.zrange(unionKey, offset, offset + limit);
                    return new CountListResult(result, totalSize);
                } finally {
                    jedis.del(unionKey);
                }
            }
        });
    }

    @Override
    public CountListResult<String> listMyReceiveRequest(final String mySn, final long offset, final long limit) {
        final String unionKey = RedisKeyUtils.getUnionKey(mySn);
        return jedisTemplate.execute(new JedisCallback<CountListResult<String>>() {
            @Override
            public CountListResult<String> doInRedis(Jedis jedis) throws Exception {
                try {
                    long totalSize = jedis.zunionstore(unionKey, RedisKeyUtils.getMyRequestedKey(mySn), RedisKeyUtils.getFriendKey(mySn), RedisKeyUtils.getSpeicalFriendKey(mySn));
                    if (totalSize == 0 || totalSize < offset) {
                        return null;
                    }
                    Set<String> result = jedis.zrange(unionKey, offset, offset + limit);
                    return new CountListResult(result, totalSize);
                } finally {
                    jedis.del(unionKey);
                }
            }
        });
    }

    @Override
    public CountListResult<String> listFriend(final String mySn, final long offset, final long limit) {
        final String key = RedisKeyUtils.getFriendKey(mySn);
        return jedisTemplate.execute(new JedisCallback<CountListResult<String>>() {
            @Override
            public CountListResult<String> doInRedis(Jedis jedis) throws Exception {
                long totalSize = jedis.zcard(key);
                if (totalSize == 0 || totalSize < offset) {
                    return null;
                }
                Set<String> result = jedis.zrange(key, offset, offset + limit);
                return new CountListResult<>(result, totalSize);
            }
        });
    }

    @Override
    public CountListResult<String> listSpecialFriend(final String mySn, final long offset, final long limit) {
        final String key = RedisKeyUtils.getSpeicalFriendKey(mySn);
        return jedisTemplate.execute(new JedisCallback<CountListResult<String>>() {
            @Override
            public CountListResult<String> doInRedis(Jedis jedis) throws Exception {
                long totalSize = jedis.zcard(key);
                if (totalSize == 0 || totalSize < offset) {
                    return null;
                }
                Set<String> result = jedis.zrange(key, offset, offset + limit);
                return new CountListResult<>(result, totalSize);
            }
        });
    }

    @Override
    public String getFriendRemark(final String currentSn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                return jedis.hget(RedisKeyUtils.getFriendRemarkKey(currentSn), friendSn);
            }
        });
    }

    @Override
    public String getRelation(final String currentSn, final String sn) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                if (jedis.zrank(RedisKeyUtils.getSpeicalFriendKey(currentSn), sn) != null) {
                    return RelationShip.SPECIAL_FRIEND.getCode();
                } else if (jedis.zrank(RedisKeyUtils.getFriendKey(currentSn), sn) != null) {
                    return RelationShip.FRIEND.getCode();
                }
                if (jedis.zrank(RedisKeyUtils.getMyRequestKey(currentSn), sn) != null) {
                    return RelationShip.TO_ADD.getCode();
                } else if (jedis.zrank(RedisKeyUtils.getMyRequestedKey(currentSn), sn) != null) {
                    return RelationShip.BE_ADDED.getCode();
                } else {
                    return null;
                }
            }
        });
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    /**
     * 删除好友
     *
     * @param currentSn
     * @param friendSn
     * @return 返回friendSn
     */
    @Override
    public String delFriend(final String currentSn, final String friendSn) {
        return jedisTemplate.execute(new JedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) throws Exception {
                //删除我的好友列表记录
                jedis.zrem(RedisKeyUtils.getFriendKey(currentSn), friendSn);
                //删除对方列表记录
                jedis.zrem(RedisKeyUtils.getFriendKey(friendSn), currentSn);
                return friendSn;
            }
        });
    }
}
