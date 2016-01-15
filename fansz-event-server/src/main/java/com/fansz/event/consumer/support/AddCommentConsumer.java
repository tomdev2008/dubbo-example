package com.fansz.event.consumer.support;

import com.alibaba.fastjson.JSON;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.entity.PushComment;
import com.fansz.db.entity.PushPost;
import com.fansz.db.repository.NewsfeedsCommentDAO;
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
import org.springframework.util.Assert;

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

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Override
    public void onEvent(ConsumerRecord<String, String> record) {
        final AddCommentEvent addCommentEvent = JSON.parseObject(record.value(), AddCommentEvent.class);
        if (InformationSource.NEWSFEEDS.equals(addCommentEvent.getSource())) {//朋友圈的评论
            CountListResult<String> commentFriendList = relationTemplate.listFriend(addCommentEvent.getMemberSn(), 0, FRIEND_LIMIT);//获取评论人的好友
            if (commentFriendList.getTotalCount() == 0) {
                return;
            }
            NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(addCommentEvent.getPostId());
            CountListResult<String> postFriendList = relationTemplate.listFriend(newsfeedsPost.getMemberSn(), 0, FRIEND_LIMIT);//获取发帖人的好友
            if (postFriendList.getTotalCount() == 0) {
                return;
            }
            //发表评论的人与POST CREATOR的共同好友
            Collection bothFriedns = CollectionUtils.intersection(commentFriendList.getResult(), postFriendList.getResult());
            if (addCommentEvent.getCommentParentId() == null) {
                //直接评论,评论人与POST creator的共同好友可见(如果是自己评论自己,则共同好友还是全部好友)
                if (!addCommentEvent.getMemberSn().equals(newsfeedsPost.getMemberSn())) {
                    //如果不是自己评论自己的post, 评论需要发给post creator
                    bothFriedns.add(newsfeedsPost.getMemberSn());
                }
                //推送给朋友
                savePushComment(bothFriedns, addCommentEvent);
            } else {
                //回复他人评论. 评论人,被回复人,Post creator三方共同好友可见.
                NewsfeedsPostComment parentComment = newsfeedsCommentDAO.load(addCommentEvent.getCommentParentId());
                //获取parentComment评论人的好友
                CountListResult<String> parentCommentFriendList = relationTemplate.listFriend(parentComment.getCommentatorSn(), 0, FRIEND_LIMIT);
                if (parentCommentFriendList.getTotalCount() == 0) {
                    return;
                }
                //发表评论的人与被回复人以及post creator的共同好友
                Collection allFriends = CollectionUtils.intersection(bothFriedns, parentCommentFriendList.getResult());
                //被回复人
                allFriends.add(parentComment.getCommentatorSn());
                if (!addCommentEvent.getMemberSn().equals(newsfeedsPost.getMemberSn())) {
                    //如果不是自己评论自己的post, 评论需要发给post creator
                    allFriends.add(newsfeedsPost.getMemberSn());
                }
                savePushComment(allFriends, addCommentEvent);
            }

        }
    }

    private void savePushComment(Collection friends, AddCommentEvent addCommentEvent) {
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
