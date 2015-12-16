package com.fansz.members.redis.support;

import org.springframework.dao.DataAccessException;
import redis.clients.jedis.Jedis;

/**
 * Created by allan on 15/12/16.
 */
public interface JedisCallback<T> {

    /**
     * @param jedis active Redis connection
     * @return a result object or {@code null} if none
     * @throws DataAccessException
     */
    T doInRedis(Jedis jedis) throws Exception;
}
