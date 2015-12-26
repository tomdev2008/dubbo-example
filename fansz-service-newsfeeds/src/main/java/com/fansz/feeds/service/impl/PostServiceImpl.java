package com.fansz.feeds.service.impl;


import com.fansz.db.entity.NewsfeedsPostEntity;
import com.fansz.db.entity.UserEntity;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.PostService;
import com.fansz.newsfeed.model.post.AddPostParam;
import com.fansz.newsfeed.model.post.GetPostByIdParam;
import com.fansz.newsfeed.model.post.PostInfoResult;
import com.fansz.newsfeed.model.profile.UserInfoResult;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 15-11-3.
 */
@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public Long addPost(AddPostParam addPostParam) {
        NewsfeedsPostEntity entity = BeanTools.copyAs(addPostParam, NewsfeedsPostEntity.class);
        newsfeedsPostDAO.save(entity);
        PublishPostEvent publishPostEvent = new PublishPostEvent(entity.getId(), addPostParam.getCurrentSn(),entity.getPostTime());
        eventProducer.produce(AsyncEventType.PUBLISH_POST, publishPostEvent);
        return entity.getId();
    }

    @Override
    public PostInfoResult getPost(GetPostByIdParam postParam) {
        NewsfeedsPostEntity entity = newsfeedsPostDAO.load(postParam.getPostId());
        PostInfoResult result = BeanTools.copyAs(entity, PostInfoResult.class);
        UserEntity userEntity = userDAO.findBySn(entity.getMemberSn());
        UserInfoResult userInfoResult = BeanTools.copyAs(userEntity, UserInfoResult.class);
        result.setUserInfoResult(userInfoResult);
        return result;
    }
}
