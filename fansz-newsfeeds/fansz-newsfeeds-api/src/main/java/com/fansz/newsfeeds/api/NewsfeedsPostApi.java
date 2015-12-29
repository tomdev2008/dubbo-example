package com.fansz.newsfeeds.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.newsfeeds.model.post.*;


/**
 * newsfeeds帖子相关接口
 */
@DubboxService("newsfeeds")
public interface NewsfeedsPostApi {
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

    /**
     * 查询我的朋友圈内容列表：N007
     * 显示我的所有好友发表的朋友圈的内容列表
     * @param memberParam
     * @return
     * @throws ApplicationException
     */
    @DubboxMethod("showMyNewsfeedsList")
    CommonPagedResult<PostInfoResult> getMyNewsfeedsList(GetPostsParam memberParam) throws ApplicationException;
}
