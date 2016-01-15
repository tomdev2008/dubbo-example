package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.PushLike;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.PushLikeDAO;
import com.fansz.event.model.AddLikeEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.model.CountListResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;

/**
 * 点赞异步事件消费者
 */
@Component("addLikeConsumer")
public class AddLikeConsumer implements IEventConsumer {

    @Autowired
    private PushLikeDAO pushLikeDAO;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;


    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final AddLikeEvent addLikeEvent = JSON.parseObject(record.value(), AddLikeEvent.class);
        if (InformationSource.NEWSFEEDS.equals(addLikeEvent.getSource())) {
            CountListResult<String> friendList = relationTemplate.listFriend(addLikeEvent.getMemberSn(), 0, FRIEND_LIMIT);
            if (friendList.getTotalCount() == 0) {
                return;
            }
            NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(addLikeEvent.getPostId());
            //如果是自己点赞,需要给我的好友看到
            if(addLikeEvent.getMemberSn().equals(newsfeedsPost.getMemberSn())){
                savePushLike(friendList.getResult(), addLikeEvent);
                return;
            }
            CountListResult<String> postFriendList = relationTemplate.listFriend(newsfeedsPost.getMemberSn(), 0, FRIEND_LIMIT);//获取发帖人的好友
            if (postFriendList.getTotalCount() == 0) {
                return;
            }
            //推送给共同好友 & post creator
            Collection sameFriends = CollectionUtils.intersection(friendList.getResult(), postFriendList.getResult());
            sameFriends.add(newsfeedsPost.getMemberSn());
            savePushLike(sameFriends, addLikeEvent);
        }
    }

    private void savePushLike(Collection friends, AddLikeEvent addLikeEvent){
        if (!CollectionTools.isNullOrEmpty(friends)) {
            Date now = DateTools.getSysDate();
            for (Object friendSn : friends) {//将点赞推送给好友
                PushLike pushLike = new PushLike();
                pushLike.setMemberSn((String) friendSn);
                pushLike.setPostId(addLikeEvent.getPostId());
                pushLike.setCreatetime(now);
                pushLike.setLikeId(addLikeEvent.getLikeId());
                pushLikeDAO.save(pushLike);
            }
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.ADD_LIKE;
    }
}
