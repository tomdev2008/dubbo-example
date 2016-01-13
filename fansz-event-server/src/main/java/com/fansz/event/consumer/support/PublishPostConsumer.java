package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.*;
import com.fansz.db.repository.*;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.model.CountListResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 15/12/21.
 */
@Component("publishPostConsumer")
public class PublishPostConsumer implements IEventConsumer {


    @Autowired
    private PushPostDAO pushPostDAO;

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private FandomPostDAO fandomPostDAO;

    @Autowired
    private FandomDAO fandomDAO;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final PublishPostEvent publishPostEvent = JSON.parseObject(record.value(), PublishPostEvent.class);
        Long postId = publishPostEvent.getPostId();
        if (publishPostEvent.getSource().equals(InformationSource.FANDOM)) {//如果是在fandom发的帖子,需要在newsfeeds中增加记录
            NewsfeedsPost entity = BeanTools.copyAs(publishPostEvent, NewsfeedsPost.class);

            FandomPost fandomPost = fandomPostDAO.load(postId);
            //获取post所在fandom的名称
            Fandom fandom = fandomDAO.load(fandomPost.getFandomId());
            entity.setSourceFrom(InformationSource.FANDOM.getCode());
            entity.setSourcePostId(postId);
            entity.setSourceFandomName(fandom.getFandomName());
            newsfeedsPostDAO.save(entity);
            postId = entity.getId();

            //同步推送到自己的朋友圈
            PushPost myPost = new PushPost();
            myPost.setMemberSn(publishPostEvent.getMemberSn());
            myPost.setPostId(postId);
            myPost.setCreatetime(entity.getPostTime());
            pushPostDAO.save(myPost);
        }

        CountListResult<String> friendList = relationTemplate.listFriend(publishPostEvent.getMemberSn(), 0, FRIEND_LIMIT);
        if (friendList.getTotalCount() == 0) {
            return;
        }
        //推送给朋友
        for (String friendSn : friendList.getResult()) {
            PushPost pushPost = new PushPost();
            pushPost.setMemberSn(friendSn);
            pushPost.setPostId(postId);
            pushPost.setCreatetime(publishPostEvent.getPostTime());
            pushPostDAO.save(pushPost);
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.PUBLISH_POST;
    }
}
