package com.fansz.fandom.service.impl;

import com.fansz.common.provider.constant.RelationShip;
import com.fansz.common.provider.utils.RedisKeyUtils;
import com.fansz.fandom.service.RelationService;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * Created by allan on 16/1/8.
 */
@Component("relationService")
public class RelationServiceImpl implements RelationService {
    @Resource(name = "jedisTemplate")
    private JedisTemplate jedisTemplate;

    /**
     * 获取用户间的关系
     *
     * @param currentSn 当前用户
     * @param sn        其他用户
     * @return
     */
    @Override
    public String getRelation(final String currentSn, final String sn) {
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
