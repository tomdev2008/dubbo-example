package com.fansz.members.api.provider;

import com.fansz.members.api.FandomApi;
import com.fansz.members.api.service.FandomService;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.post.GetPostsParam;
import com.fansz.members.model.post.PostInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 圈子接口类
 * Created by root on 15-11-3.
 */
@Component("fandomProvider")
public class FandomProvider implements FandomApi{

    @Autowired
    private FandomService fandomService;

    @Override
    public Response addFandom(FandomParam form) {
        return null;
    }

    @Override
    public CommonResult<FandomInfoResult> getFandom(FandomQueryParam fandomQueryParam) {
        return null;
    }

    @Override
    public CommonResult<PostInfoResult> getPostsByFandom(PostsQueryParam param) {
        return null;
    }

    @Override
    public CommonResult<List<FandomInfoResult>> getFandomsByCategory(String categoryId) {
        return null;
    }

    @Override
    public Response followFandom(String id) {
        return null;
    }

    @Override
    public Response unfollowFandom(String id) {
        return null;
    }

    @Override
    public Response getRecommendFandom() {
        return null;
    }

    @Override
    public Response getSubCategory(String id) {
        return null;
    }

    @Override
    public Response getCategory() {
        return null;
    }

    @Override
    public Response getFandomsBySubCategory(String id) {
        return null;
    }

    @Override
    public Response followerOfFandom(FandomFollowers fandomFollowers) {
        return null;
    }
}

