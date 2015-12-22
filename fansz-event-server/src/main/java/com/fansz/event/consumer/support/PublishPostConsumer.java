package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.members.model.event.AsyncEventType;
import com.fansz.members.model.event.PublishPostEvent;
import com.fansz.members.model.event.SmsEvent;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.support.JedisCallback;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Set;

/**
 * Created by allan on 15/12/21.
 */
@Component("publishPostConsumer")
public class PublishPostConsumer implements IEventConsumer {
    @Autowired
    private JedisTemplate jedisTemplate;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final PublishPostEvent publishPostEvent = JSON.parseObject(record.value(), PublishPostEvent.class);
        jedisTemplate.execute(new JedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) throws Exception {
                String friendKey = publishPostEvent.getMemberSn() + ".friends";
                Set<String> friendsSet = jedis.smembers(friendKey);
                if (CollectionTools.isNullOrEmpty(friendsSet)) {
                    return false;
                }
                Pipeline pipe = jedis.pipelined();
                for (String uid : friendsSet) {
                    pipe.zadd(uid + ".feeds", System.currentTimeMillis(), String.valueOf(publishPostEvent.getPostId()));
                }
                pipe.sync();
                return true;
            }
        });
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.PUBLISH_POST;
    }
}
