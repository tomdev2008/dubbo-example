package com.fansz.feeds.service.impl;


import com.fansz.db.entity.*;
import com.fansz.db.model.NewsfeedsCommentVO;
import com.fansz.db.model.NewsfeedsMemberLikeVO;
import com.fansz.db.model.NewsfeedsPostVO;
import com.fansz.db.repository.*;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.profile.UserInfoResult;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service("postService")
public class NewsfeedsPostServiceImpl implements NewsfeedsPostService {

    @Autowired
    private NewsfeedsPostDAO newsfeedsPostDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private PushPostDAO pushPostDAO;

    @Autowired
    private NewsfeedsMemberLikeDAO newsfeedsMemberLikeDAO;

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

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
    public QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(String memberSn, Page page) {
        QueryResult<PushPost> pushPosts = pushPostDAO.findPushPostByMemberSn(page, memberSn);
        QueryResult<PostInfoResult> postResult = new QueryResult<>();

        if(!CollectionTools.isNullOrEmpty(pushPosts.getResultlist())){
            //获取postIds查询参数
            List<String> postIds = new ArrayList<>();
            for(PushPost pushPost: pushPosts.getResultlist()){
                postIds.add(pushPost.getPostId().toString());
            }

            HashSet<String> memberSnSet = new HashSet<>();
            //所有的post
            List<NewsfeedsPostVO> newsfeedsPosts = newsfeedsPostDAO.findNewsfeedsPostByIds(postIds);
            for(NewsfeedsPostVO post: newsfeedsPosts){
                memberSnSet.add(post.getMemberSn());
            }
            //TODO 所有的评论
            List<NewsfeedsCommentVO> commentList = newsfeedsCommentDAO.findByPostIdsAndMemberSn(postIds, memberSn);

            //所有的like
            List<NewsfeedsMemberLikeVO> memberLikeList = newsfeedsMemberLikeDAO.findLikesByPostIdsAndMemberSn(postIds, memberSn);
            for(NewsfeedsMemberLikeVO memberLike: memberLikeList){
                memberSnSet.add(memberLike.getMemberSn());
            }
            List<String> memberSnList = new ArrayList<>(memberSnSet);


            //NewsfeedsPost convert to PostInfoResult
            List<PostInfoResult> postInfoResultList = BeanTools.copyAs(newsfeedsPosts, PostInfoResult.class);

            //批量查询所需的user info
            //TODO 从缓存中获取user information
            List<User> userList = userDAO.findBySnString(memberSnList);
            //遍历memberLikeList, 往PostInfoResult中赋值
            for(NewsfeedsMemberLikeVO memberLike: memberLikeList){
                PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", memberLike.getPostId());
                User user = CollectionTools.find(userList, "sn", memberLike.getMemberSn());
                UserInfoResult userInfoResult = BeanTools.copyAs(user, UserInfoResult.class);
                if(CollectionTools.isNullOrEmpty(postInfoResult.getLikedList())){
                    postInfoResult.setLikedList(new ArrayList<UserInfoResult>());
                }
                postInfoResult.getLikedList().add(userInfoResult);
                if(memberSn.equals(memberLike.getMemberSn())){
                    postInfoResult.setLiked(Boolean.TRUE.toString());
                }
            }

//            List<PostCommentQueryResult> commentQueryResultList
            //遍历comment
            List<PostCommentQueryResult> commentQueryResultList = BeanTools.copyAs(commentList, PostCommentQueryResult.class);
            for(PostCommentQueryResult postComment: commentQueryResultList){
                User commentUser = CollectionTools.find(userList, "sn", postComment.getCommentatorSn());
                postComment.setCommentatorAvatar(commentUser.getMemberAvatar());
                //find parent comment && set value
                if(postComment.getCommentParentId() != null){
                    PostCommentQueryResult originComment = CollectionTools.find(commentQueryResultList, "id", postComment.getCommentParentId());
                    postComment.setOrginAvatar(originComment.getCommentatorAvatar());
                    postComment.setOrginContent(originComment.getCommentContent());
                    postComment.setOrginNickname(originComment.getCommentatorNickname());
                    postComment.setOrginSn(originComment.getCommentatorSn());
                }

                PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", postComment.getPostId());
                if(CollectionTools.isNullOrEmpty(postInfoResult.getCommentList())){
                    postInfoResult.setCommentList(new ArrayList<PostCommentQueryResult>());
                }
                postInfoResult.getCommentList().add(postComment);
            }

            //发布用户信息
            for(PostInfoResult postInfoResult: postInfoResultList){
                User postUser = CollectionTools.find(userList, "sn", memberSn);
                UserInfoResult postUserInfo = BeanTools.copyAs(postUser, UserInfoResult.class);
                postInfoResult.setUserInfoResult(postUserInfo);
            }

            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postInfoResultList.size());

            return postResult;
        }else {
            return new QueryResult<>(null, 0);
        }
    }
}
