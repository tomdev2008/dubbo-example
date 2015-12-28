package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.PushPost;
import com.fansz.db.entity.UserRelation;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.PushPostDAO;
import com.fansz.db.repository.UserRelationDAO;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.type.AsyncEventType;


import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.redis.JedisTemplate;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by allan on 15/12/21.
 */
@Component("publishPostConsumer")
public class PublishPostConsumer implements IEventConsumer {
    @Autowired
    private JedisTemplate jedisTemplate;

    @Autowired
    private PushPostDAO pushPostDAO;

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private UserRelationDAO userRelationDAO;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final PublishPostEvent publishPostEvent = JSON.parseObject(record.value(), PublishPostEvent.class);
        Long postId = publishPostEvent.getPostId();
        if (publishPostEvent.getSource().equals(InformationSource.FANDOM)) {//如果是在fandom发的帖子,需要在newsfeeds中增加记录
            NewsfeedsPost entity = BeanTools.copyAs(publishPostEvent, NewsfeedsPost.class);
            entity.setSourceFrom(InformationSource.FANDOM.getCode());
            entity.setSourcePostId(postId);
            newsfeedsPostDAO.save(entity);
            postId = entity.getId();
        }
        List<UserRelation> friendList = userRelationDAO.findMyFriends(publishPostEvent.getMemberSn());
        if (CollectionTools.isNullOrEmpty(friendList)) {
            return;
        }
        for (UserRelation friend : friendList) {
            PushPost pushPost = new PushPost();
            pushPost.setMemberSn(friend.getFriendMemberSn());
            pushPost.setPostId(postId);
            pushPost.setCeratetime(new Date());
            pushPostDAO.save(pushPost);
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.PUBLISH_POST;
    }
}
