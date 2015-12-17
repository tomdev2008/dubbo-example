package com.fansz.members.redis;

import com.fansz.members.redis.support.JedisCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

@Component("jedisTemplate")
public class JedisTemplate implements JedisOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

    @Resource(name = "jedisSentinelPool")
    private JedisSentinelPool jedisPool;

    @Override
    public Jedis getRedisClient() {
        try {
            Jedis shardJedis = jedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            logger.error("can't get jedit from pool!", e);
        }
        return null;
    }

    @Override
    public void returnResource(Jedis jedis) {
        jedis.close();
    }

    public <T> T execute(JedisCallback<T> redisCallback) {
        Jedis jedis = getRedisClient();
        if (jedis == null) {
            return null;
        }
        try {
            return redisCallback.doInRedis(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            returnResource(jedis);
        }
        return null;
    }
}
