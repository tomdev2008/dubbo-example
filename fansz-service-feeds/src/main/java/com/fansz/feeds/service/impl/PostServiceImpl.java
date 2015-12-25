package com.fansz.feeds.service.impl;


import com.fansz.pub.utils.BeanTools;
import com.fansz.service.api.entity.FandomPostEntity;
import com.fansz.service.api.entity.FandomPostLikeEntity;
import com.fansz.service.api.repository.FandomPostEntityMapper;
import com.fansz.service.api.repository.FandomPostLikeEntityMapper;
import com.fansz.service.api.service.PostService;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.post.*;
import com.fansz.service.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    public Long addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = BeanTools.copyAs(addPostParam, FandomPostEntity.class);
        fandomPostEntity.setMemberSn(addPostParam.getCurrentSn());
        fandomPostEntity.setPostTime(new Date());
        fandomPostEntityMapper.insert(fandomPostEntity);
        return fandomPostEntity.getId();
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
        return this.fandomPostEntityMapper.listTimedMemberFandomPosts(getMemberFandomPostsParam.getFandomId(), getMemberFandomPostsParam.getCurrentSn(), null, pageBounds);
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
}
