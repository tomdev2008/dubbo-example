package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.PushComment;
import com.fansz.db.entity.PushLike;
import com.fansz.db.repository.PushLikeDAO;
import com.fansz.event.model.AddLikeEvent;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.redis.RelationTemplate;
import com.fansz.redis.model.CountListResult;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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


    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final AddLikeEvent addLikeEvent = JSON.parseObject(record.value(), AddLikeEvent.class);
        if (InformationSource.NEWSFEEDS.equals(addLikeEvent.getSource())) {
            CountListResult<String> friendList = relationTemplate.listFriend(addLikeEvent.getMemberSn(), 0, FRIEND_LIMIT);
            if (friendList.getTotalCount() == 0) {
                return;
            }
            //推送给朋友
            Date now = DateTools.getSysDate();
            for (String friendSn : friendList.getResult()) {//将回复推送到好友
                PushLike pushLike = new PushLike();
                pushLike.setMemberSn(friendSn);
                pushLike.setPostId(addLikeEvent.getPostId());
                pushLike.setCreatetime(now);
                pushLikeDAO.save(pushLike);
            }
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.ADD_LIKE;
    }
}
