package com.fansz.members.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

@Component("redisSessionFactory")
public class RedisSessionFactoryImpl implements RedisSessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionFactoryImpl.class);

    @Resource(name="jedisSentinelPool")
    private JedisSentinelPool jedisPool;

    @Override
    public Jedis getRedisClient() {
        try {
            Jedis shardJedis = jedisPool.getResource();
            return shardJedis;
        }
        catch (Exception e) {
            logger.error("getRedisClent error", e);
        }
        return null;
    }

    @Override
    public void returnResource(Jedis jedis) {
        jedis.close();
    }

}
