package com.fansz.feeds.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.NewsfeedsMemberLike;
import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.entity.User;
import com.fansz.db.model.NewsFeedsFandomPostVO;
import com.fansz.db.model.NewsfeedsCommentVO;
import com.fansz.db.model.NewsfeedsMemberLikeVO;
import com.fansz.db.repository.NewsfeedsCommentDAO;
import com.fansz.db.repository.NewsfeedsMemberLikeDAO;
import com.fansz.db.repository.NewsfeedsPostDAO;
import com.fansz.db.repository.UserDAO;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.post.RemovePostParam;
import com.fansz.newsfeeds.model.profile.UserInfoResult;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public PostInfoResult getPost(GetPostByIdParam postParam) {
        NewsfeedsPost newsfeedsPost = newsfeedsPostDAO.load(postParam.getPostId());
        List<NewsfeedsPost> newsfeedsPostList = new ArrayList<>();
        newsfeedsPostList.add(newsfeedsPost);
        return this.assemblePostInfoResult(newsfeedsPostList, postParam.getCurrentSn()).get(0);
    }

    @Override
    public NewsfeedsPost deletePostById(RemovePostParam postParam) {
        Map<String, Object> map = new HashMap();
        map.put("id", postParam.getPostId());
        List<NewsfeedsPost> newsfeedsPostList = newsfeedsPostDAO.findBy(map);
        if (null != newsfeedsPostList && newsfeedsPostList.size() > 0) {
            NewsfeedsPost newsfeedsPost = newsfeedsPostList.get(0);
            if (!newsfeedsPost.getMemberSn().equals(postParam.getCurrentSn())) {
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
        int count = newsfeedsMemberLikeDAO.isLiked(postParam.getCurrentSn(), postParam.getPostId());
        if (count > 0) {
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
        Map<String, Object> map = new HashMap();
        map.put("memberSn", postParam.getCurrentSn());
        map.put("postId", postParam.getPostId());
        List<NewsfeedsMemberLike> newsfeedsMemberLikeList = newsfeedsMemberLikeDAO.findBy(map);
        if (CollectionTools.isNullOrEmpty(newsfeedsMemberLikeList)) {
            return ErrorCode.LIKED_NO_DELETE.getCode();
        }
        newsfeedsMemberLikeDAO.delete(newsfeedsMemberLikeList.get(0));
        return null;
    }

    @Override
    public QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(String memberSn, Page page) {
        QueryResult<PostInfoResult> postResult = new QueryResult<>(null, 0);
        QueryResult<NewsfeedsPost> postQueryResult = newsfeedsPostDAO.findNewsfeedsPostByPushPostMemberSn(page, memberSn);
        if (!CollectionTools.isNullOrEmpty(postQueryResult.getResultlist())) {
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(postQueryResult.getResultlist(), memberSn);
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postInfoResultList.size());
        }
        return postResult;
    }

    @Override
    public QueryResult<PostInfoResult> findFriendsNewsfeedsListBySn(String memberSn, String friendSn, Page page) {
        QueryResult<PostInfoResult> postResult = new QueryResult<>(null, 0);
        QueryResult<NewsfeedsPost> newsfeedsPosts = newsfeedsPostDAO.findNewsfeedsPostBySn(page, friendSn);
        if (!CollectionTools.isNullOrEmpty(newsfeedsPosts.getResultlist())) {
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(newsfeedsPosts.getResultlist(), memberSn);
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postInfoResultList.size());
        }
        return postResult;
    }

    /**
     * 组装postInfoResult
     *
     * @param newsfeedsPosts postList
     * @param memberSn       登陆用户sn
     * @return
     */
    private List<PostInfoResult> assemblePostInfoResult(List<? extends NewsfeedsPost> newsfeedsPosts, String memberSn) {
        //所有的post
        if (CollectionTools.isNullOrEmpty(newsfeedsPosts)) {
            return null;
        }
        HashSet<String> memberSnSet = new HashSet<>();
        HashSet<String> postIdSet = new HashSet<>();
        HashSet<String> fandomPostIdSet = new HashSet<>();
        for (NewsfeedsPost post : newsfeedsPosts) {
            memberSnSet.add(post.getMemberSn());
            postIdSet.add(String.valueOf(post.getId()));
            if (InformationSource.FANDOM.getCode().equals(post.getSourceFrom())) {
                fandomPostIdSet.add(String.valueOf(post.getSourcePostId()));
            }
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

        //查询所有朋友圈动态的fandom信息
        List<NewsFeedsFandomPostVO> newsFeedsFandomPostVOs = null;
        if (!CollectionTools.isNullOrEmpty(fandomPostIdSet)) {
            newsFeedsFandomPostVOs = newsfeedsPostDAO.findNewsfeedsFandomPostInfoByPostId(new ArrayList<String>(fandomPostIdSet));
        }

        //NewsfeedsPost convert to PostInfoResult
        List<PostInfoResult> postInfoResultList = new ArrayList<>();
        //convert newsfeedPost to PostInfoResult
        for (NewsfeedsPost newsfeedsPost : newsfeedsPosts) {
            PostInfoResult postInfoResult = BeanTools.copyAs(newsfeedsPost, PostInfoResult.class);
            User postUser = CollectionTools.find(userList, "sn", newsfeedsPost.getMemberSn());
            UserInfoResult postUserInfo = BeanTools.copyAs(postUser, UserInfoResult.class);
            postInfoResult.setUserInfoResult(postUserInfo);
            //liked default 0
            postInfoResult.setLiked("0");
            //如果该朋友圈是来自fandom的动态,补充fandom信息
            if (InformationSource.FANDOM.getCode().equals(newsfeedsPost.getSourceFrom()) && newsfeedsPost.getSourcePostId() != null) {
                NewsFeedsFandomPostVO fandomPostVO = CollectionTools.find(newsFeedsFandomPostVOs, "id", newsfeedsPost.getSourcePostId());
                if (fandomPostVO != null) {
                    postInfoResult.setFandomId(String.valueOf(fandomPostVO.getFandomId()));
                    postInfoResult.setFandomAvatarUrl(fandomPostVO.getFandomAvatarUrl());
                    postInfoResult.setFandomName(fandomPostVO.getFandomName());
                }
            }

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
            postInfoResult.setLiked(memberSn.equals(memberLike.getMemberSn()) ? "1" : "0");
        }
        //遍历comment
        List<PostCommentQueryResult> commentQueryResultList = BeanTools.copyAs(commentList, PostCommentQueryResult.class);
        for (PostCommentQueryResult postComment : commentQueryResultList) {
            User commentUser = CollectionTools.find(userList, "sn", postComment.getCommentatorSn());
            postComment.setCommentatorNickname(commentUser.getNickname());
            postComment.setCommentatorAvatar(commentUser.getMemberAvatar());
            //find parent comment && set value
            if (postComment.getCommentParentId() != null) {
                PostCommentQueryResult originComment = CollectionTools.find(commentQueryResultList, "id", postComment.getCommentParentId());
                postComment.setOriginAvatar(originComment.getCommentatorAvatar());
                postComment.setOriginContent(originComment.getCommentContent());
                postComment.setOriginNickname(originComment.getCommentatorNickname());
                postComment.setOriginSn(originComment.getCommentatorSn());
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
