package com.fansz.members.api.service.impl;

import com.fansz.members.model.post.AddPostParam;
import com.fansz.pub.utils.BeanTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by allan on 15/12/22.
 */
@Service
public class PostServiceImpl2 extends PostServiceImpl {

    @Resource(name="jedisTemplate")
    private JedisTemplate jedisTemplate;

    @Override
    public Long addPost(final AddPostParam addPostParam) {
        final Long pk = generateId();

        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                Map<String, Object> objMap = BeanTools.getProperties(addPostParam);
                for (String prop : objMap.keySet()) {
                    jedis.hset(pk + ".post", prop, String.valueOf(objMap.get(prop)));
                }
                return true;
            }
        });
        return pk;
    }

    private Long generateId() {
        return jedisTemplate.execute(new JedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) throws Exception {
                return jedis.incr("pk.post");
            }
        });
    }
}
