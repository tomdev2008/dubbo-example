package com.fansz.members.api.service;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by root on 15-11-3.
 */
@Service
public interface PostService {

    FandomPostEntity addPost(UserEntity user, com.fansz.members.model.post.PostParam postParam);

    void removePost(String id);

    FandomPostEntity getPost(UserEntity user, String id);

    void likePost(UserEntity user, String id);

    void unlikePost(UserEntity user, String id);

    List<FandomPostEntity> getFriendPosts(UserEntity user, String friendId);

    List<FandomPostEntity> getFriendsPosts(UserEntity user, PageBounds pagePara);

    List<FandomPostEntity> getAllFandomPosts(UserEntity user, PageBounds pagePara);
}
