package com.fansz.members.api.service;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.fandom.FandomFollowers;
import com.fansz.members.model.fandom.PostsQueryParam;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    FandomEntity getFandom(Long userId, Long  fandomId);

    List<FandomPostEntity> getPostsByFandom(PostsQueryParam param);

    List<FandomEntity> getFandomsByCategoryId(Long userId, String categoryId);

    void followFandom(Long userId, String id);

    void unfollowFandom(Long userId, String id);

    List<FandomEntity> getRecommendFandom(String id);

    List<UserEntity> followerOfFandom(FandomFollowers fandomFollowers);
}
