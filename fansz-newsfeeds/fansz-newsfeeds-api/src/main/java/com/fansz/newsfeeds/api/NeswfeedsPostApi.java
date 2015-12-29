package com.fansz.newsfeeds.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.newsfeeds.model.post.AddPostParam;
import com.fansz.newsfeeds.model.post.GetPostByIdParam;
import com.fansz.newsfeeds.model.post.PostInfoResult;
import com.fansz.newsfeeds.model.post.RemovePostParam;
import sun.applet.AppletListener;


/**
 * newsfeeds帖子相关接口
 */
@DubboxService("newsfeeds")
public interface NeswfeedsPostApi {
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
    @DubboxMethod("delMyNewsfeedPost")
    CommonResult<PostInfoResult> removePost(RemovePostParam removePostrParam) throws ApplicationException;

    /**
     * 根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @DubboxMethod("getNewsfeedsPost")
    CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException;

    /**
     * 给朋友圈内容点赞
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @DubboxMethod("voteNewsfeedLike")
    CommonResult<NullResult> voteNewsfeedLike(GetPostByIdParam postParam)throws ApplicationException;

    /**
     * 取消给朋友圈内容点赞
     * @param postParam
     * @return
     * @throws ApplicationException
     */
    @DubboxMethod("delNewsfeedLike")
    CommonResult<NullResult> delNewsfeedLike(GetPostByIdParam postParam)throws ApplicationException;

}
