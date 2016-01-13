package com.fansz.feeds.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.db.entity.*;
import com.fansz.db.model.NewsFeedsFandomPostVO;
import com.fansz.db.model.NewsfeedsCommentVO;
import com.fansz.db.model.NewsfeedsMemberLikeVO;
import com.fansz.db.repository.*;
import com.fansz.event.model.AddLikeEvent;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.producer.EventProducer;
import com.fansz.event.type.AsyncEventType;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import com.fansz.newsfeeds.model.post.*;
import com.fansz.newsfeeds.model.profile.UserInfoResult;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.CollectionTools;
import com.fansz.pub.utils.DateTools;
import com.fansz.redis.UserTemplate;
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
    private PushPostDAO pushPostDAO;

    @Autowired
    private NewsfeedsCommentDAO newsfeedsCommentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private NewsfeedsMemberLikeDAO newsfeedsMemberLikeDAO;

    @Autowired
    private PushLikeDAO pushLikeDAO;

    @Autowired
    private UserTemplate userTemplate;


    @Override
    public Long addPost(AddPostParam addPostParam) {
        Date now = DateTools.getSysDate();
        NewsfeedsPost entity = new NewsfeedsPost();
        entity.setPostTitle(addPostParam.getPostTitle());
        entity.setPostContent(addPostParam.getPostContent());
        entity.setPostTime(now);
        entity.setMemberSn(addPostParam.getCurrentSn());
        entity.setSourceFrom(InformationSource.NEWSFEEDS.getCode());
        newsfeedsPostDAO.save(entity);

        //由于用户发帖之后,希望自己能马上看到,因此必须同步推送到自己到名下,但对于朋友圈,可以采用异步;
        PushPost myPost = new PushPost();
        myPost.setMemberSn(addPostParam.getCurrentSn());
        myPost.setPostId(entity.getId());
        myPost.setCreatetime(now);
        pushPostDAO.save(myPost);

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

        PushLike pushLike = new PushLike();
        pushLike.setMemberSn(postParam.getCurrentSn());
        pushLike.setPostId(postParam.getPostId());
        pushLike.setCreatetime(DateTools.getSysDate());
        pushLikeDAO.save(pushLike);

        AddLikeEvent addLikeEvent = new AddLikeEvent(newsfeedsMemberLike.getId(), newsfeedsMemberLike.getPostId(), newsfeedsMemberLike.getMemberSn());
        eventProducer.produce(AsyncEventType.ADD_LIKE, addLikeEvent);
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
    public QueryResult<PostInfoResult> findNewsfeedsListByMemberSn(GetPostsParam postsParam) {
        Page page = new Page();
        page.setPage(postsParam.getPageNum());
        page.setPageSize(postsParam.getPageSize());
        if (postsParam.getSinceId() > 0 || postsParam.getMaxId() > 0) {
            page.setPageSize(1);
        }
        QueryResult<PostInfoResult> postResult = new QueryResult<>(null, 0);
        QueryResult<NewsfeedsPost> postQueryResult = newsfeedsPostDAO.findNewsfeedsPostByPushPostMemberSn(page, postsParam.getCurrentSn(), postsParam.getSinceId(), postsParam.getMaxId());
        if (!CollectionTools.isNullOrEmpty(postQueryResult.getResultlist())) {
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(postQueryResult.getResultlist(), postsParam.getCurrentSn());
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(postQueryResult.getTotalrecord());
        }
        return postResult;
    }

    @Override
    public QueryResult<PostInfoResult> findFriendsNewsfeedsListBySn(GetMemberPostsParam memberPostsParam) {
        Page page = new Page();
        page.setPage(memberPostsParam.getPageNum());
        page.setPageSize(memberPostsParam.getPageSize());
        if (memberPostsParam.getSinceId() > 0 || memberPostsParam.getMaxId() > 0) {
            page.setPageSize(1);
        }
        QueryResult<PostInfoResult> postResult = new QueryResult<>(null, 0);
        QueryResult<NewsfeedsPost> newsfeedsPosts = newsfeedsPostDAO.findNewsfeedsPostBySn(page, memberPostsParam.getFriendSn(), memberPostsParam.getSinceId(), memberPostsParam.getMaxId());
        if (!CollectionTools.isNullOrEmpty(newsfeedsPosts.getResultlist())) {
            List<PostInfoResult> postInfoResultList = this.assemblePostInfoResult(newsfeedsPosts.getResultlist(), memberPostsParam.getCurrentSn());
            postResult.setResultlist(postInfoResultList);
            postResult.setTotalrecord(newsfeedsPosts.getTotalrecord());
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
        //临时存储本次用到的所有用户信息, key为sn, value为redis返回的hash
        Map<String, Map<String, Object>> allMemberMap = new HashMap<>();
        HashSet<String> postIdSet = new HashSet<>();
        for (NewsfeedsPost post : newsfeedsPosts) {
            memberSnSet.add(post.getMemberSn());
            postIdSet.add(String.valueOf(post.getId()));
        }
        List<String> postIds = new ArrayList<>(postIdSet);
        //所有的comment
        List<NewsfeedsCommentVO> commentList = newsfeedsCommentDAO.findByPostIdsAndMemberSn(postIds, memberSn);
        for (NewsfeedsCommentVO commentVO : commentList) {
            memberSnSet.add(commentVO.getCommentatorSn());
        }

        //所有的like
        List<NewsfeedsMemberLikeVO> memberLikeList = newsfeedsMemberLikeDAO.findLikesByPostIdsAndMemberSn(postIds, memberSn);
        for (NewsfeedsMemberLikeVO memberLike : memberLikeList) {
            memberSnSet.add(memberLike.getMemberSn());
        }
        //批量查询所需的user info
        for (String sn : memberSnSet) {
            Map<String, String> userMap = userTemplate.get(sn);
            Map<String, Object> userInfoResult = new HashMap<>();
            userInfoResult.putAll(userMap);
            allMemberMap.put(sn, userInfoResult);
        }

        //NewsfeedsPost convert to PostInfoResult
        List<PostInfoResult> postInfoResultList = new ArrayList<>();
        //convert newsfeedPost to PostInfoResult
        for (NewsfeedsPost newsfeedsPost : newsfeedsPosts) {
            PostInfoResult postInfoResult = BeanTools.copyAs(newsfeedsPost, PostInfoResult.class);
            Map<String, Object> userInfoResult = allMemberMap.get(newsfeedsPost.getMemberSn());
            postInfoResult.setUserInfoResult(userInfoResult);
            //liked default 0
            postInfoResult.setLiked("0");
            //如果该朋友圈是来自fandom的动态,补充fandom name
            if (InformationSource.FANDOM.getCode().equals(newsfeedsPost.getSourceFrom()) && newsfeedsPost.getSourcePostId() != null) {
                postInfoResult.setFandomName(newsfeedsPost.getSourceFandomName());
            }
            postInfoResultList.add(postInfoResult);
        }

        //遍历memberLikeList, 往PostInfoResult中赋值
        for (NewsfeedsMemberLikeVO memberLike : memberLikeList) {
            PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", memberLike.getPostId());
            if (postInfoResult == null) {
                continue;
            }
            Map<String, Object> userInfoResult = allMemberMap.get(memberLike.getMemberSn());
            if (CollectionTools.isNullOrEmpty(postInfoResult.getLikedList())) {
                postInfoResult.setLikedList(new ArrayList<Map<String, Object>>());
            }
            postInfoResult.getLikedList().add(userInfoResult);
            if ("0".equals(postInfoResult.getLiked())) {
                postInfoResult.setLiked(memberSn.equals(memberLike.getMemberSn()) ? "1" : "0");
            }
        }
        //遍历comment
        List<PostCommentQueryResult> commentQueryResultList = BeanTools.copyAs(commentList, PostCommentQueryResult.class);
        for (PostCommentQueryResult postComment : commentQueryResultList) {
            PostInfoResult postInfoResult = CollectionTools.find(postInfoResultList, "id", postComment.getPostId());
            if (postInfoResult == null) {
                continue;
            }

            Map<String, Object> userMap = allMemberMap.get(postComment.getCommentatorSn());
            postComment.setCommentatorNickname(userMap.get("nickname") == null ? "" : (String) userMap.get("nickname"));
            postComment.setCommentatorAvatar((String) userMap.get("member_avatar"));
            //find parent comment && set value
            if (postComment.getCommentParentId() != null) {
                PostCommentQueryResult originComment = CollectionTools.find(commentQueryResultList, "id", postComment.getCommentParentId());
                if (originComment != null) {
                    postComment.setOriginAvatar(originComment.getCommentatorAvatar());
                    postComment.setOriginContent(originComment.getCommentContent());
                    postComment.setOriginNickname(originComment.getCommentatorNickname());
                    postComment.setOriginSn(originComment.getCommentatorSn());
                }
            }

            if (CollectionTools.isNullOrEmpty(postInfoResult.getCommentList())) {
                postInfoResult.setCommentList(new ArrayList<PostCommentQueryResult>());
            }
            postInfoResult.getCommentList().add(postComment);
        }


        return postInfoResultList;
    }
}
