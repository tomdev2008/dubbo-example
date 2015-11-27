package com.fansz.members.api.service;

import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.model.fandom.*;

import com.fansz.members.model.post.GetPostsParam;

import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {

    FandomInfoResult addFandom(FandomParam fandomParam);

    FandomInfoResult getFandom(NormalFandomPara fandomPara);

    List<FandomPostEntity> getPostsByFandom(GetPostsParam param);


    List<FandomInfoResult> getFandomsByCategoryId(FandomByCategory fandomByCategory);

    void followFandom(NormalFandomPara fandomPara);

    void unfollowFandom(Integer id);

    List<FandomInfoResult> getRecommendFandom(String id);

    List<UserEntity> followerOfFandom(FandomFollowers fandomFollowers);
}
