package com.fansz.feeds.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.NewsfeedsMemberLike;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.User;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.db.repository.NewsfeedsMemberLikeDAO;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.post.RemovePostParam;
import com.fansz.newsfeeds.model.profile.UserInfoResult;
import com.fansz.pub.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 15-11-3.
 */
@Service("newsfeedsPostService")
public class NewsfeedsPostServiceImpl implements NewsfeedsPostService {

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private NewsfeedsMemberLikeDAO newsfeedsMemberLikeDAO;

    @Override
    public Long addPost(AddPostParam addPostParam) {
        NewsfeedsPost entity = BeanTools.copyAs(addPostParam, NewsfeedsPost.class);
        newsfeedsPostDAO.save(entity);
        PublishPostEvent publishPostEvent = new PublishPostEvent(entity.getId(), addPostParam.getCurrentSn(),entity.getPostTime());
        eventProducer.produce(AsyncEventType.PUBLISH_POST, publishPostEvent);
        return entity.getId();
    }

    @Override
    public PostInfoResult getPost(GetPostByIdParam postParam) {
        NewsfeedsPost entity = newsfeedsPostDAO.load(postParam.getPostId());
        PostInfoResult result = BeanTools.copyAs(entity, PostInfoResult.class);
        User user = userDAO.findBySn(entity.getMemberSn());
        UserInfoResult userInfoResult = BeanTools.copyAs(user, UserInfoResult.class);
        result.setUserInfoResult(userInfoResult);
        return result;
    }

    @Override
    public NewsfeedsPost deletePostById(RemovePostParam postParam) {
        Map<String,Object> map = new HashMap();
        map.put("id",postParam.getPostId());
        List<NewsfeedsPost> newsfeedsPostList = newsfeedsPostDAO.findBy(map);
        if(null != newsfeedsPostList && newsfeedsPostList.size() > 0){
            NewsfeedsPost newsfeedsPost = newsfeedsPostList.get(0);
            if(!newsfeedsPost.getMemberSn().equals(postParam.getCurrentSn())){
                return null;
            }
            //删除当前post下所有评论
            newsfeedsCommentDAO.removeCommetByPostId(postParam.getPostId());
            //删除当前post下所有点赞
            newsfeedsMemberLikeDAO.removeLikeByPostId(postParam.getPostId());
            //删除自己post信息
            newsfeedsPostDAO.delete(newsfeedsPost);
            return newsfeedsPost;
        }
        return null;
    }

    @Override
    public String saveNewsfeedLike(GetPostByIdParam postParam) {
        //判断是否重复点赞
        int count = newsfeedsMemberLikeDAO.isLiked(postParam.getCurrentSn(),postParam.getPostId());
        if(count > 0){
            return ErrorCode.LIKED_REPEATED.getCode();
        }
        NewsfeedsMemberLike newsfeedsMemberLike = new NewsfeedsMemberLike();
        newsfeedsMemberLike.setPostId(postParam.getPostId());
        newsfeedsMemberLike.setMemberSn(postParam.getCurrentSn());
        newsfeedsMemberLikeDAO.save(newsfeedsMemberLike);
        return null;
    }

    @Override
    public String deleteNewsfeedLike(GetPostByIdParam postParam) {
        Map<String,Object> map = new HashMap();
        map.put("memberSn",postParam.getCurrentSn());
        map.put("postId",postParam.getPostId());
        List<NewsfeedsMemberLike> newsfeedsMemberLikeList = newsfeedsMemberLikeDAO.findBy(map);
        if(null == newsfeedsMemberLikeList || newsfeedsMemberLikeList.size() == 0){
            return ErrorCode.LIKED_NO_DELETE.getCode();
        }
        newsfeedsMemberLikeDAO.delete(newsfeedsMemberLikeList.get(0));
        return null;
    }
}
