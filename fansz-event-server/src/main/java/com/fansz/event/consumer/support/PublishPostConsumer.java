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
import com.fansz.pub.utils.JsonHelper;
import com.fansz.redis.JedisTemplate;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.model.CountListResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(PublishPostConsumer.class);

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

    @Resource(name = "searchClient")
    private Client searchClient;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final PublishPostEvent publishPostEvent = JSON.parseObject(record.value(), PublishPostEvent.class);
        Long postId = publishPostEvent.getPostId();
        InformationSource source = publishPostEvent.getSource();
        switch (source) {
            case FANDOM://如果是在fandom发的帖子,需要在newsfeeds中增加记录
                FandomPost fandomPost = fandomPostDAO.load(postId);
                //获取post所在fandom的名称
                if (fandomPost == null) {//防止用户发帖之后马上删除,但是异步任务还未结束的场景
                    return;
                }
                Fandom fandom = fandomDAO.load(fandomPost.getFandomId());
                NewsfeedsPost entity = BeanTools.copyAs(publishPostEvent, NewsfeedsPost.class);
                entity.setSourceFrom(InformationSource.FANDOM.getCode());
                entity.setSourcePostId(postId);
                entity.setSourceFandomName(fandom.getFandomName());
                entity.setSourceFandomAvatarUrl(fandom.getFandomAvatarUrl());
                entity.setSourcePostType(publishPostEvent.getPostType().getCode());
                newsfeedsPostDAO.save(entity);
                postId = entity.getId();

                //同步推送到自己的朋友圈
                PushPost myPost = new PushPost();
                myPost.setMemberSn(publishPostEvent.getPostCreator());
                myPost.setPostId(postId);
                myPost.setCreatetime(entity.getPostTime());
                pushPostDAO.save(myPost);

                //更新elasticsearch索引
                IndexResponse response=searchClient.prepareIndex("fansz", "post", String.valueOf(postId)).setRefresh(true).setSource(JsonHelper.convertObject2JSONString(fandomPost)).setTTL(2000).execute().actionGet();
                logger.debug(response.toString());
                break;
            case NEWSFEEDS:
                NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(postId);
                if (newsfeedsPost == null) {//如果用户马上删除了,则不会推送到朋友圈
                    return;
                }
                break;
            default:
                logger.warn("unknow post source:{},record", source, record);
                return;
        }

        CountListResult<String> friendList = relationTemplate.listFriend(publishPostEvent.getPostCreator(), 0, FRIEND_LIMIT);
        if (friendList == null || friendList.getTotalCount() == 0) {
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
