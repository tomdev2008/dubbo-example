package com.fansz.members.api.service.impl;

import com.fansz.members.api.entity.FandomEntity;
import com.fansz.members.api.entity.FandomPostEntity;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.model.fandom.FandomFollowers;
import com.fansz.members.model.fandom.PostsQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 15-11-4.
 */
@Service
public class FandomServiceImpl implements FandomService {

    public FandomEntity getFandom(Long userId, Long fandomId) {
        return null;
    }

    public List<FandomPostEntity> getPostsByFandom(PostsQueryParam param) {
        return null;
    }

    public List<FandomEntity> getFandomsByCategoryId(Long userId, String categoryId) {
        return null;
    }

    public void followFandom(Long userId, String id) {
    }

    public void unfollowFandom(Long userId, String id) {
    }

    public List<FandomEntity> getRecommendFandom(String id) {
        return null;
    }

    public List<UserEntity> followerOfFandom(FandomFollowers fandomFollowers) {
        return null;
    }
}
