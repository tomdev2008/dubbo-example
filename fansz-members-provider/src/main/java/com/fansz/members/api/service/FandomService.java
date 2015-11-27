package com.fansz.members.api.service;

import com.fansz.members.model.param.FandomFollowers;
import com.fansz.members.model.param.FandomParam;
import com.fansz.members.model.param.GetPostsParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {
    public Fandom addFandom(User user, FandomParam fandomParam) throws IOException;

    Fandom getFandom(User user, String id);

    List<Post> getPostsByFandom(GetPostsParam param);

    List<Fandom> getFandomsByCategoryId(User user, String categoryId);

    void followFandom(User user, String id);

    void unfollowFandom(User user, String id);

    List<Fandom> getRecommendFandom(String id);

    List<User> followerOfFandom(FandomFollowers fandomFollowers);
}
