package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.PushComment;
import com.fansz.db.entity.PushPost;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.PushCommentDAO;
import com.fansz.event.model.AddCommentEvent;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.type.AsyncEventType;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.BeanTools;
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
 * 添加评论消费者
 */
@Component("addCommentConsumer")
public class AddCommentConsumer implements IEventConsumer {
    @Autowired
    private PushCommentDAO pushCommentDAO;
    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Resource(name = "relationTemplate")
    private RelationTemplate relationTemplate;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final AddCommentEvent addCommentEvent = JSON.parseObject(record.value(), AddCommentEvent.class);
        if (InformationSource.NEWSFEEDS.equals(addCommentEvent.getSource())) {//朋友圈的评论
            CountListResult<String> commentFriendList = relationTemplate.listFriend(addCommentEvent.getMemberSn(), 0, FRIEND_LIMIT);//获取评论人的好友
            if (commentFriendList.getTotalCount() == 0) {
                return;
            }
            NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(addCommentEvent.getPostId());
            //如果是自己评论自己,跳过.评论时,已经添加过,不需要异步添加.
            if(addCommentEvent.getMemberSn().equals(newsfeedsPost.getMemberSn())){
                savePushComment(commentFriendList.getResult(), addCommentEvent);
                return;
            }
            CountListResult<String> postFriendList = relationTemplate.listFriend(newsfeedsPost.getMemberSn(), 0, FRIEND_LIMIT);//获取发帖人的好友
            if (postFriendList.getTotalCount() == 0) {
                return;
            }
            Collection sameFriends = CollectionUtils.intersection(commentFriendList.getResult(), postFriendList.getResult());
            //评论需要发给post creator
            sameFriends.add(newsfeedsPost.getMemberSn());
            //推送给朋友
            savePushComment(sameFriends, addCommentEvent);
        }
    }

    private void savePushComment(Collection friends, AddCommentEvent addCommentEvent){
        if (!CollectionTools.isNullOrEmpty(friends)) {
            Date now = DateTools.getSysDate();
            for (Object friendSn : friends) {//将回复推送到好友
                PushComment pushComment = new PushComment();
                pushComment.setMemberSn((String) friendSn);
                pushComment.setPostId(addCommentEvent.getPostId());
                pushComment.setCommentId(addCommentEvent.getCommentId());
                pushComment.setCreatetime(now);
                pushCommentDAO.save(pushComment);
            }
        }
    }

    @Override
    public AsyncEventType getEventType() {
        return AsyncEventType.ADD_COMMENT;
    }
}
