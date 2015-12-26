package com.fansz.newsfeed.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.newsfeed.model.post.AddPostParam;
import com.fansz.newsfeed.model.post.GetPostByIdParam;
import com.fansz.newsfeed.model.post.PostInfoResult;
import com.fansz.newsfeed.model.post.RemovePostParam;


/**
 * newsfeeds帖子相关接口
 */
@DubboxService("newsfeeds")
public interface PostApi {
    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @DubboxMethod("publishNewsfeedsPost")
    CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) throws ApplicationException;

    /**
     * 删除帖子接口
     *
     * @param removePostrParam 帖子id
     * @return resp 返回对象
     */
    @DubboxMethod("deleteNewsfeedsPost")
    CommonResult<NullResult> removePost(RemovePostParam removePostrParam) throws ApplicationException;

    /**
     * 根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @DubboxMethod("getNewsfeedsPost")
    CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException;

}
