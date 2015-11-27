package com.fansz.members.api.service.impl;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.PostService;
import com.fansz.members.model.post.PostParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
@Service
public class PostServiceImpl implements PostService {


    @Override
    public FandomPostEntity addPost(UserEntity user, PostParam postParam) {
    return null;

    }

    @Override
    public void removePost(String id) {

    }

    @Override
    public FandomPostEntity getPost(UserEntity user, String id) {
     return null;
    }

    @Override
    public void likePost(UserEntity user, String id) {


    }

    @Override
    public void unlikePost(UserEntity user, String id) {


    }

    @Override
    public List<FandomPostEntity> getFriendPosts(UserEntity user, String friendId) {
        return null;
    }

    @Override
    public List<FandomPostEntity> getFriendsPosts(UserEntity user, PageBounds pagePara) {
        return null;
    }

    @Override
    public List<FandomPostEntity> getAllFandomPosts(UserEntity user, PageBounds pagePara) {
        return null;
    }

}
