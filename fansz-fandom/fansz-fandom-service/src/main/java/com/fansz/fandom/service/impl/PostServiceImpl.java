package com.fansz.fandom.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.entity.FandomPostEntity;
import com.fansz.fandom.entity.FandomPostLikeEntity;
import com.fansz.fandom.model.fandom.FandomInfoResult;
import com.fansz.fandom.model.post.*;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.fansz.fandom.model.vote.VotePostParam;
import com.fansz.fandom.model.vote.VotePostResult;
import com.fansz.fandom.model.vote.VoteResultByPostId;
import com.fansz.fandom.repository.FandomMapper;
import com.fansz.fandom.repository.FandomPostEntityMapper;
import com.fansz.fandom.repository.FandomPostLikeEntityMapper;
import com.fansz.fandom.service.AsyncEventService;
import com.fansz.fandom.service.PostService;
import com.fansz.fandom.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.fansz.pub.utils.JsonHelper;
import com.fansz.pub.utils.StringTools;
import com.fansz.redis.UserTemplate;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by root on 15-11-3.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private FandomMapper fandomMapper;

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;

    @Autowired
    private FandomPostLikeEntityMapper fandomPostLikeEntityMapper;

    @Autowired
    private AsyncEventService asyncEventService;

    @Resource(name = "userTemplate")
    private UserTemplate userTemplate;

    @Resource(name = "searchClient")
    private Client searchClient;

    @Override
    public FandomPostEntity addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = BeanTools.copyAs(addPostParam, FandomPostEntity.class);
        fandomPostEntity.setMemberSn(addPostParam.getCurrentSn());
        fandomPostEntity.setPostTime(new Date());
        fandomPostEntityMapper.insert(fandomPostEntity);
        if ("1".equals(addPostParam.getPostNewsfeeds())) {//发布到朋友圈
            asyncEventService.addPost(fandomPostEntity.getId(), fandomPostEntity.getPostTime(), addPostParam);
        }

        return fandomPostEntity;
    }

    @Override
    public void removePost(RemovePostParam removePostrParam) {
        FandomPostEntity fandomPostEntity = fandomPostEntityMapper.selectByPrimaryKey(removePostrParam.getPostId());
        if (fandomPostEntity == null) {
            throw new ApplicationException(ErrorCode.POST_NOT_EXISTS);
        }
        if (!fandomPostEntity.getMemberSn().equals(removePostrParam.getCurrentSn())) {
            throw new ApplicationException(ErrorCode.POST_NOT_ALLOW_DEL);
        }
        fandomPostEntityMapper.deleteByPrimaryKey(removePostrParam.getPostId());
    }

    @Override
    public PostInfoResult getPost(GetPostByIdParam postParam) {
        return fandomPostEntityMapper.getPost(postParam);
    }

    @Override
    public List<PostLikeInfoResult> listPostVotes(PostParam postParam) {
        List<PostLikeInfoResult> list = fandomPostLikeEntityMapper.listPostVotes(postParam.getPostId());
        return list;
    }

    @Override
    public PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFandoms(memberSn, pageBounds);
    }

    @Override
    public PageList<PostInfoResult> getFriendsPosts(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFriends(memberSn, pageBounds);
    }


    @Override
    public void addLike(AddLikeParam addLikeParam) {
        int existed = fandomPostLikeEntityMapper.isLiked(addLikeParam.getCurrentSn(), addLikeParam.getPostId());
        if (existed > 0) {
            throw new ApplicationException(ErrorCode.LIKED_REPEATED);
        }
        FandomPostLikeEntity entity = new FandomPostLikeEntity();
        entity.setPostId(addLikeParam.getPostId());
        entity.setMemberSn(addLikeParam.getCurrentSn());
        entity.setLikeTime(new Date());
        this.fandomPostLikeEntityMapper.insert(entity);
        fandomPostEntityMapper.incrLikeCountById(addLikeParam.getPostId());
        asyncEventService.addLike(entity);
    }

    @Override
    public void deleteLike(DeleteLikeParam deleteLikeParam) {
        int deleteCount = this.fandomPostLikeEntityMapper.deleteMyLike(deleteLikeParam.getCurrentSn(), deleteLikeParam.getPostId());
        if (deleteCount == 0) {
            throw new ApplicationException(ErrorCode.LIKED_NO_DELETE);
        }
        fandomPostEntityMapper.decrLikeCountById(deleteLikeParam.getPostId());

    }

    @Override
    public PageList<PostInfoResult> getMemberFandomPosts(GetMemberFandomPostsParam getMemberFandomPostsParam) {
        PageBounds pageBounds = new PageBounds(getMemberFandomPostsParam.getPageNum(), getMemberFandomPostsParam.getPageSize());
        return this.fandomPostEntityMapper.listTimedMemberFandomPosts(getMemberFandomPostsParam.getFandomId(), getMemberFandomPostsParam.getMemberSn(), null, pageBounds);
    }

    /**
     * @Override public PageList<PostInfoResult> searchPosts(SearchPostParam searchPostParam) {
     * PageBounds pageBounds = new PageBounds(searchPostParam.getPageNum(), searchPostParam.getPageSize());
     * return fandomPostEntityMapper.searchPosts(searchPostParam.getSearchVal(), pageBounds);
     * }
     */
    /**
     * 根据关键字搜索fandom post
     *
     * @param searchPostParam
     * @return
     */
    @Override
    public PageList<PostInfoResult> searchPosts(SearchPostParam searchPostParam) {
        PageBounds pageBounds = new PageBounds(searchPostParam.getPageNum(), searchPostParam.getPageSize());
        PageList<PostInfoResult> EMPTY = new PageList<>(new Paginator(pageBounds.getPage(), pageBounds.getLimit(), 0));

        SearchRequestBuilder builder = searchClient.prepareSearch(Constants.INDEX_NAME).setTypes(Constants.TYPE_POST).setSearchType(SearchType.DEFAULT).setFrom(pageBounds.getOffset()).setSize(pageBounds.getLimit());
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (StringTools.isBlank(searchPostParam.getSearchVal())) {
            return EMPTY;
        }
        qb.should(new QueryStringQueryBuilder(searchPostParam.getSearchVal()).field("post_title").field("post_content"));
        builder.setQuery(qb);
        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        logger.debug("查询到post记录总数={}", hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length == 0) {
            return EMPTY;
        }


        PageList<PostInfoResult> postList = new PageList<>(new Paginator(pageBounds.getPage(), pageBounds.getLimit(), (int) hits.getTotalHits()));
        Set<String> fandomIdList = new HashSet<>();
        for (SearchHit hit : searchHists) {
            Map<String, Object> props = hit.getSource();
            PostInfoResult post = JsonHelper.copyAs(props, PostInfoResult.class);

            //补足用户信息
            Map<String, String> userMap = userTemplate.get((String) props.get("member_sn"));
            UserInfoResult userInfo = JsonHelper.copyMapAs(userMap, UserInfoResult.class);
            post.setUserInfoResult(userInfo);

            //补足fandom信息
            String fandomId = (String) props.get("fandom_id");
            FandomInfoResult fandom = new FandomInfoResult();
            fandom.setId(Long.valueOf(fandomId));
            post.setFandomInfoResult(fandom);
            fandomIdList.add(fandomId);


            postList.add(post);
        }
        List<FandomInfoResult> fandomList = fandomMapper.getFandomByIds(fandomIdList);
        Map<Long, FandomInfoResult> fandomMap = new HashMap<>();
        for (FandomInfoResult fandom : fandomList) {
            fandomMap.put(fandom.getId(), fandom);
        }

        for (PostInfoResult post : postList) {
            post.setFandomInfoResult(fandomMap.get(post.getFandomInfoResult().getId()));
        }
        return postList;


    }

    @Override
    public PageList<PostInfoResult> getFandomPosts(PostsQueryParam postsQueryParam) {
        PageList<PostInfoResult> entities = null;
        PageBounds pageBounds = new PageBounds(postsQueryParam.getPageNum(), postsQueryParam.getPageSize());
        if ("new".equals(postsQueryParam.getType())) {
            entities = fandomPostEntityMapper.listTimedMemberFandomPosts(postsQueryParam.getFandomId(), null, postsQueryParam.getCurrentSn(), pageBounds);
        } else if ("hot".equals(postsQueryParam.getType())) {
            entities = fandomPostEntityMapper.listHotMemberFandomPosts(postsQueryParam.getFandomId(), null, postsQueryParam.getCurrentSn(), pageBounds);
        } else if ("vote".equals(postsQueryParam.getType())) {
            entities = fandomPostEntityMapper.listVoteMemberFandomPosts(postsQueryParam.getFandomId(), null, postsQueryParam.getCurrentSn(), pageBounds);
        }

        return entities;
    }

    @Override
    public PageList<PostInfoResult> getPostsAllByMember(GetMemberPostsParam postParam) {
        PageBounds pageBounds = new PageBounds(postParam.getPageNum(), postParam.getPageSize());
        return fandomPostEntityMapper.getPostsAllByMember(postParam.getCurrentSn(), postParam.getFriendSn(), pageBounds);
    }

    /**
     * 投票
     *
     * @param votePostParam
     * @return
     */
    @Override
    public VotePostResult votePost(VotePostParam votePostParam) {
        Map<String, Object> map = fandomPostEntityMapper.getVerifyVoteInfo(votePostParam.getCurrentSn(), votePostParam.getPostId());
        if (map.get("id") == null) { //记录不存在
            throw new ApplicationException(ErrorCode.VOTE_POST_NOT_EXIST);
        }
        if (map.get("effective_time") != null) {
            Date effectiveTime = (Date) map.get("effective_time");
            //判断是否超过截止时间
            if (effectiveTime.getTime() < System.currentTimeMillis()) {
                throw new ApplicationException(ErrorCode.VOTE_EXPIRED);
            }
        }
        Long count = (Long) map.get("isVote");
        //判断是否重复投票
        if (count > 0) {
            throw new ApplicationException(ErrorCode.VOTE_REPEATED);
        }
        //保存投票信息
        fandomPostEntityMapper.votePost(votePostParam);
        //统计投票信息
        VotePostResult votePostResult = fandomPostEntityMapper.getVoteResultByPostId(votePostParam.getPostId());
        Integer voteCount = votePostResult.getOptionACount() + votePostResult.getOptionBCount(); //总的投票数
        //更新投票帖 投票总数
        fandomPostEntityMapper.updatePostVoteCount(voteCount, votePostParam.getPostId());
        return votePostResult;
    }

    /**
     * 获取投票帖投票结果
     *
     * @param voteResultByPostId
     * @return
     */
    @Override
    public VotePostResult getVoteResultByPostId(VoteResultByPostId voteResultByPostId) {
        return fandomPostEntityMapper.getVoteResultByPostId(voteResultByPostId.getPostId());
    }
}
