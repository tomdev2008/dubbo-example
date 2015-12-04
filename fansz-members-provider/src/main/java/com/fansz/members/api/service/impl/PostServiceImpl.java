package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.repository.FandomPostEntityMapper;
import com.fansz.members.api.service.PostService;
import com.fansz.members.model.post.AddPostParam;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.api.repository.FandomPostLikeEntityMapper;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.post.PostParam;
import com.fansz.members.tools.BeanTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private FandomPostEntityMapper fandomPostEntityMapper;
    private FandomPostLikeEntityMapper fandomPostLikeEntityMapper;

    @Override
    public FandomPostEntity addPost(AddPostParam addPostParam) {
        FandomPostEntity fandomPostEntity = BeanTools.copyAs(addPostParam, FandomPostEntity.class);
        fandomPostEntityMapper.insert(fandomPostEntity);
        return fandomPostEntity;
    }

    @Override
    public void removePost(String id) {

    }

    @Override
    public FandomPostEntity getPost(UserEntity user, String id) {
        return null;
    }

    @Override
    public List<PostLikeInfoResult> listPostVotes(PostParam postParam) {
        List<PostLikeInfoResult> list = fandomPostLikeEntityMapper.listPostVotes(postParam.getPostId());
        return list;
    }

    @Override
    public void unlikePost(UserEntity user, String id) {


    }

    @Override
    public PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFandoms(memberSn, pageBounds);
    }

    @Override
    public PageList<PostInfoResult> getFriendsPosts(String memberSn, PageBounds pageBounds) {
        return fandomPostEntityMapper.findPostsOfMyFriends(memberSn, pageBounds);
    }
}
