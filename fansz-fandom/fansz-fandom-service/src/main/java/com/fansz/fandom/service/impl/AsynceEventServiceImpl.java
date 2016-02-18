package com.fansz.fandom.service.impl;

import com.fansz.event.model.AddCommentEvent;
import com.fansz.event.model.AddLikeEvent;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.fandom.entity.FandomPostEntity;
import com.fansz.fandom.entity.FandomPostLikeEntity;
import com.fansz.fandom.entity.PostCommentEntity;
import com.fansz.fandom.model.post.AddLikeParam;
import com.fansz.fandom.model.post.AddPostParam;
import com.fansz.fandom.model.post.GetPostByIdParam;
import com.fansz.fandom.repository.FandomPostEntityMapper;
import com.fansz.fandom.service.AsyncEventService;
import com.fansz.pub.constant.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by allan on 16/2/1.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AsynceEventServiceImpl implements AsyncEventService {

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;


    @Override
    public void addLike(FandomPostLikeEntity postLikeEntity) {
        AddLikeEvent addLikeEvent = new AddLikeEvent(postLikeEntity.getId(), postLikeEntity.getPostId(), postLikeEntity.getMemberSn());
        FandomPostEntity fandomPostEntity = fandomPostEntityMapper.selectByPrimaryKey(postLikeEntity.getPostId());
        addLikeEvent.setFandomId(fandomPostEntity.getFandomId());
        addLikeEvent.setPostCreator(fandomPostEntity.getMemberSn());
        eventProducer.produce(AsyncEventType.ADD_LIKE, addLikeEvent);
    }

    public void addPost(Long postId, Date postTime, AddPostParam addPostParam) {
        if ("1".equals(addPostParam.getPostNewsfeeds())) {//发布到朋友圈
            PublishPostEvent postPublishEvent = new PublishPostEvent(postId, addPostParam.getCurrentSn(), postTime, addPostParam.getPostTitle(), addPostParam.getPostContent(), PostType.getTypeByCode(addPostParam.getPostType()));
            eventProducer.produce(AsyncEventType.PUBLISH_POST, postPublishEvent);
        }
    }

    @Override
    public void addComment(PostCommentEntity postComment) {
        FandomPostEntity fandomPostEntity = fandomPostEntityMapper.selectByPrimaryKey(postComment.getPostId());
        AddCommentEvent addCommentEvent = new AddCommentEvent(postComment.getId(), postComment.getPostId(), postComment.getCommentParentId(), postComment.getCommentatorSn(), postComment.getCommentContent());
        addCommentEvent.setFandomId(fandomPostEntity.getFandomId());
        addCommentEvent.setPostCreator(fandomPostEntity.getMemberSn());
        eventProducer.produce(AsyncEventType.ADD_COMMENT, addCommentEvent);
    }

    @Override
    public void onUserChanged(Map<String, Object> user) {
        eventProducer.produce(AsyncEventType.USER, (String)user.get("sn"), user);
    }
}
