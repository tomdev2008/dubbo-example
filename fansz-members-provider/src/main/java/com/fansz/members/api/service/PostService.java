package com.fansz.members.api.service;


import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.post.PostInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
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

    PageList<PostInfoResult> getFriendsPosts(String memberSn,PageBounds pageBounds);

    PageList<PostInfoResult> findPostsOfMyFandoms(String memberSn,PageBounds pageBounds);
}
