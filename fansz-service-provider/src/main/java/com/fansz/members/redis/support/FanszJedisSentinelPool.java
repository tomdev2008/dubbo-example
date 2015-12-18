package com.fansz.members.redis.support;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Created by allan on 15/12/15.
 */
public class FanszJedisSentinelPool extends JedisSentinelPool {
    public FanszJedisSentinelPool(String masterName, String sentinels, GenericObjectPoolConfig poolConfig, String password) {
        super(masterName, StringUtils.commaDelimitedListToSet(sentinels), poolConfig, password);
    }


}
