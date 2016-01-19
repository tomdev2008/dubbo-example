package com.fansz.fandom.service.impl;


import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.fandom.entity.FandomPostEntity;
import com.fansz.fandom.entity.FandomPostLikeEntity;
import com.fansz.fandom.model.post.*;
import com.fansz.fandom.model.vote.VotePostParam;
import com.fansz.fandom.model.vote.VotePostResult;
import com.fansz.fandom.model.vote.VoteResultByPostId;
import com.fansz.fandom.repository.FandomPostEntityMapper;
import com.fansz.fandom.repository.FandomPostLikeEntityMapper;
import com.fansz.fandom.service.PostService;
import com.fansz.fandom.tools.Constants;
import com.fansz.pub.utils.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 15-11-3.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService {

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;

    @Autowired
    private FandomPostLikeEntityMapper fandomPostLikeEntityMapper;

    @Override
    public FandomPostEntity addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = BeanTools.copyAs(addPostParam, FandomPostEntity.class);
        fandomPostEntity.setMemberSn(addPostParam.getCurrentSn());
        fandomPostEntity.setPostTime(new Date());
        fandomPostEntityMapper.insert(fandomPostEntity);
        return fandomPostEntity;
    }

    @Override
    public void removePost(RemovePostParam removePostrParam) {
        FandomPostEntity fandomPostEntity = fandomPostEntityMapper.selectByPrimaryKey(removePostrParam.getPostId());
        if (fandomPostEntity == null) {
            throw new ApplicationException(Constants.POST_NOT_EXISTS, "Post not exists");
        }
        if (!fandomPostEntity.getMemberSn().equals(removePostrParam.getCurrentSn())) {
            throw new ApplicationException(Constants.POST_NOT_ALLOW_DEL, "Not your post");
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
            throw new ApplicationException(Constants.LIKED_REPEATED, "Liked repeated");
        }
        FandomPostLikeEntity entity = new FandomPostLikeEntity();
        entity.setPostId(addLikeParam.getPostId());
        entity.setMemberSn(addLikeParam.getCurrentSn());
        entity.setLikeTime(new Date());
        this.fandomPostLikeEntityMapper.insert(entity);
        fandomPostEntityMapper.incrLikeCountById(addLikeParam.getPostId());
    }

    @Override
    public void deleteLike(DeleteLikeParam deleteLikeParam) {
        int deleteCount = this.fandomPostLikeEntityMapper.deleteMyLike(deleteLikeParam.getCurrentSn(), deleteLikeParam.getPostId());
        if (deleteCount == 0) {
            throw new ApplicationException(Constants.LIKED_NO_DELETE, "Need authority to delete");
        }
        fandomPostEntityMapper.decrLikeCountById(deleteLikeParam.getPostId());

    }

    @Override
    public PageList<PostInfoResult> getMemberFandomPosts(GetMemberFandomPostsParam getMemberFandomPostsParam) {
        PageBounds pageBounds = new PageBounds(getMemberFandomPostsParam.getPageNum(), getMemberFandomPostsParam.getPageSize());
        return this.fandomPostEntityMapper.listTimedMemberFandomPosts(getMemberFandomPostsParam.getFandomId(), getMemberFandomPostsParam.getMemberSn(), null, pageBounds);
    }

    @Override
    public PageList<PostInfoResult> searchPosts(SearchPostParam searchPostParam) {
        PageBounds pageBounds = new PageBounds(searchPostParam.getPageNum(), searchPostParam.getPageSize());
        return fandomPostEntityMapper.searchPosts(searchPostParam.getSearchVal(), pageBounds);
    }

    @Override
    public PageList<PostInfoResult> getFandomPosts(PostsQueryParam postsQueryParam) {
        PageList<PostInfoResult> entities = null;
        PageBounds pageBounds = new PageBounds(postsQueryParam.getPageNum(), postsQueryParam.getPageSize());
        if ("new".equals(postsQueryParam.getType())) {
            entities = fandomPostEntityMapper.listTimedMemberFandomPosts(postsQueryParam.getFandomId(), null, postsQueryParam.getCurrentSn(), pageBounds);
        } else {
            entities = fandomPostEntityMapper.listHotMemberFandomPosts(postsQueryParam.getFandomId(), null, postsQueryParam.getCurrentSn(), pageBounds);
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
     * @param votePostParam
     * @return
     */
    @Override
    public VotePostResult votePost(VotePostParam votePostParam) {
        Map<String,Object> map = fandomPostEntityMapper.getVerifyVoteInfo(votePostParam.getCurrentSn(),votePostParam.getPostId());
        if(map.get("id") == null){ //记录不存在
            throw new ApplicationException(ErrorCode.VOTE_POST_NOT_EXIST);
        }
        if(map.get("effective_time") != null) {
            Date effectiveTime = (Date) map.get("effective_time");
            //判断是否超过截止时间
            if (effectiveTime.getTime() < System.currentTimeMillis()) {
                throw new ApplicationException(ErrorCode.VOTE_EXPIRED);
            }
        }
        Long count = (Long) map.get("isVote");
        //判断是否重复投票
        if(count > 0){
            throw new ApplicationException(ErrorCode.VOTE_REPEATED);
        }
        //保存投票信息
        fandomPostEntityMapper.votePost(votePostParam);
        //统计投票信息
        return fandomPostEntityMapper.getVoteResultByPostId(votePostParam.getPostId());
    }

    /**
     * 获取投票帖投票结果
     * @param voteResultByPostId
     * @return
     */
    @Override
    public VotePostResult getVoteResultByPostId(VoteResultByPostId voteResultByPostId) {
        return fandomPostEntityMapper.getVoteResultByPostId(voteResultByPostId.getPostId());
    }
}
