package com.fansz.members.redis;

import redis.clients.jedis.Jedis;

public interface RedisSessionFactory {
    Jedis getRedisClient();

    void returnResource(Jedis jedis);

}
