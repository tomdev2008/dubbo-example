package com.fansz.members.redis;

import com.fansz.members.redis.support.JedisCallback;
import redis.clients.jedis.Jedis;

public interface JedisOperations {

    Jedis getRedisClient();

    void returnResource(Jedis jedis);

    <T> T execute(JedisCallback<T> redisCallback);

}
