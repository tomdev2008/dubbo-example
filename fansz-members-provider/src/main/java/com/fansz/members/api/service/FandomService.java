package com.fansz.members.api.service;

import com.fansz.members.model.Fandom;
import com.fansz.members.model.Post;
import com.fansz.members.model.User;
import com.fansz.members.model.param.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 15-11-3.
 */
public interface FandomService {
    public Fandom addFandom(FandomParam fandomParam) throws IOException;

    Fandom getFandom(NormalFandomPara fandomPara);

    List<Post> getPostsByFandom(GetPostsParam param);

    List<Fandom> getFandomsByCategoryId(FandomByCategory fandomByCategory);

    void followFandom(NormalFandomPara fandomPara);

    void unfollowFandom(Integer id);

    List<Fandom> getRecommendFandom(String id);

    List<User> followerOfFandom(FandomFollowers fandomFollowers);
}
