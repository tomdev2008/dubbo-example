package com.fansz.feeds.service.impl;


import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.User;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.profile.UserInfoResult;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by root on 15-11-3.
 */
@Service("newsfeedsPostServiceImpl")
public class NewsfeedsPostServiceImpl implements NewsfeedsPostService {

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public Long addPost(AddPostParam addPostParam) {
        NewsfeedsPost entity = new NewsfeedsPost();
        entity.setPostTitle(addPostParam.getPostTitle());
        entity.setPostContent(addPostParam.getPostContent());
        entity.setPostTime(new Date());
        entity.setMemberSn(addPostParam.getCurrentSn());
        entity.setSourceFrom(InformationSource.NEWSFEEDS.getCode());
        newsfeedsPostDAO.save(entity);
        PublishPostEvent publishPostEvent = new PublishPostEvent(entity.getId(), addPostParam.getCurrentSn(), entity.getPostTime(), InformationSource.NEWSFEEDS);
        eventProducer.produce(AsyncEventType.PUBLISH_POST, publishPostEvent);
        return entity.getId();
    }

    @Override
    public PostInfoResult getPost(Long postId) {
        NewsfeedsPost entity = newsfeedsPostDAO.load(postId);
        PostInfoResult result = BeanTools.copyAs(entity, PostInfoResult.class);
        User user = userDAO.findBySn(entity.getMemberSn());
        UserInfoResult userInfoResult = BeanTools.copyAs(user, UserInfoResult.class);
        result.setUserInfoResult(userInfoResult);
        return result;
    }
}
