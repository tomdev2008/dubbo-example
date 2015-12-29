package com.fansz.feeds.service.impl;


import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.PushPost;
import com.fansz.db.entity.User;
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
        PublishPostEvent publishPostEvent = new PublishPostEvent(entity.getId(), addPostParam.getCurrentSn(), entity.getPostTime());
        eventProducer.produce(AsyncEventType.PUBLISH_POST, publishPostEvent);
        return entity.getId();
    }

    @Override
    public PostInfoResult getPost(GetPostByIdParam postParam) {
        NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(postParam.getPostId());
        List<NewsfeedsPost> newsfeedsPostList = new ArrayList<>();
        newsfeedsPostList.add(newsfeedsPost);
        return this.assemblePostInfoResult(newsfeedsPostList, postParam.getCurrentSn()).get(0);
    }

    @Override
    public QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(String memberSn, Page page) {
        QueryResult<PushPost> pushPosts = pushPostDAO.findPushPostByMemberSn(page, memberSn);

        if (!CollectionTools.isNullOrEmpty(pushPosts.getResultlist())) {
            QueryResult<PostInfoResult> postResult = new QueryResult<>();
            //获取postIds查询参数
            List<String> postIds = new ArrayList<>();
            for (PushPost pushPost : pushPosts.getResultlist()) {
                postIds.add(pushPost.getPostId().toString());
            }
            List<NewsfeedsPostVO> newsfeedsPosts = newsfeedsPostDAO.findNewsfeedsPostByIds(postIds);
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(newsfeedsPosts, memberSn);
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postInfoResultList.size());
            return postResult;
        } else {
            return new QueryResult<>(null, 0);
        }
    }

    @Override
    public QueryResult<PostInfoResult> findFriendsNewsfeedsListBySn(String memberSn, String friendSn, Page page) {
        QueryResult<NewsfeedsPost> newsfeedsPosts = newsfeedsPostDAO.findNewsfeedsPostBySn(page, friendSn);
        if (!CollectionTools.isNullOrEmpty(newsfeedsPosts.getResultlist())){
            QueryResult<PostInfoResult> postResult = new QueryResult<>();
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(newsfeedsPosts.getResultlist(), memberSn);
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postInfoResultList.size());
            return postResult;
        } else {
            return new QueryResult<>(null, 0);
        }
    }

    /**
     * 组装postInfoResult
     *
     * @param newsfeedsPosts postList
     * @param memberSn 登陆用户sn
     * @return
     */
    private List<PostInfoResult> assemblePostInfoResult(List<? extends NewsfeedsPost> newsfeedsPosts, String memberSn) {
        //所有的post
        if (CollectionTools.isNullOrEmpty(newsfeedsPosts)) {
            return null;
        }
        HashSet<String> memberSnSet = new HashSet<>();
        HashSet<String> postIdSet = new HashSet<>();
        for (NewsfeedsPost post : newsfeedsPosts) {
            memberSnSet.add(post.getMemberSn());
            postIdSet.add(String.valueOf(post.getId()));
        }
        List<String> postIds = new ArrayList<>(postIdSet);
        //所有的comment
        List<NewsfeedsCommentVO> commentList = newsfeedsCommentDAO.findByPostIdsAndMemberSn(postIds, memberSn);

        //所有的like
        List<NewsfeedsMemberLikeVO> memberLikeList = newsfeedsMemberLikeDAO.findLikesByPostIdsAndMemberSn(postIds, memberSn);
        for (NewsfeedsMemberLikeVO memberLike : memberLikeList) {
            memberSnSet.add(memberLike.getMemberSn());
        }
        List<String> memberSnList = new ArrayList<>(memberSnSet);

        //批量查询所需的user info
        //TODO 从缓存中获取user information
        List<User> userList = userDAO.findBySnString(memberSnList);

        //NewsfeedsPost convert to PostInfoResult
        List<PostInfoResult> postInfoResultList = new ArrayList<>();
        //convert newsfeedPost to PostInfoResult
        for(NewsfeedsPost newsfeedsPost: newsfeedsPosts){
            PostInfoResult postInfoResult = BeanTools.copyAs(newsfeedsPost, PostInfoResult.class);
            User postUser = CollectionTools.find(userList, "sn", newsfeedsPost.getMemberSn());
            UserInfoResult postUserInfo = BeanTools.copyAs(postUser, UserInfoResult.class);
            postInfoResult.setUserInfoResult(postUserInfo);
            postInfoResultList.add(postInfoResult);
        }

        //遍历memberLikeList, 往PostInfoResult中赋值
        for (NewsfeedsMemberLikeVO memberLike : memberLikeList) {
            PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", memberLike.getPostId());
            User user = CollectionTools.find(userList, "sn", memberLike.getMemberSn());
            UserInfoResult userInfoResult = BeanTools.copyAs(user, UserInfoResult.class);
            if (CollectionTools.isNullOrEmpty(postInfoResult.getLikedList())) {
                postInfoResult.setLikedList(new ArrayList<UserInfoResult>());
            }
            postInfoResult.getLikedList().add(userInfoResult);
            if (memberSn.equals(memberLike.getMemberSn())) {
                postInfoResult.setLiked(Boolean.TRUE.toString());
            }
        }
        //遍历comment
        List<PostCommentQueryResult> commentQueryResultList = BeanTools.copyAs(commentList, PostCommentQueryResult.class);
        for (PostCommentQueryResult postComment : commentQueryResultList) {
            User commentUser = CollectionTools.find(userList, "sn", postComment.getCommentatorSn());
            postComment.setCommentatorAvatar(commentUser.getMemberAvatar());
            //find parent comment && set value
            if (postComment.getCommentParentId() != null) {
                PostCommentQueryResult originComment = CollectionTools.find(commentQueryResultList, "id", postComment.getCommentParentId());
                postComment.setOrginAvatar(originComment.getCommentatorAvatar());
                postComment.setOrginContent(originComment.getCommentContent());
                postComment.setOrginNickname(originComment.getCommentatorNickname());
                postComment.setOrginSn(originComment.getCommentatorSn());
            }

            PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", postComment.getPostId());
            if (CollectionTools.isNullOrEmpty(postInfoResult.getCommentList())) {
                postInfoResult.setCommentList(new ArrayList<PostCommentQueryResult>());
            }
            postInfoResult.getCommentList().add(postComment);
        }


        return postInfoResultList;
    }
}
