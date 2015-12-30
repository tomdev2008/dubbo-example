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
    @DubboxMethod("publishNewfeed")
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
     * 单查某一条朋友圈的详情:N008
     * 根据postid来查询post的详情,APP可根据缓存跳转
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @DubboxMethod("showNewsfeedsDetail")
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

    /**
     * 查询某一个人发布的所有的所有朋友圈内容:N009
     * 根据会员号sn查询某人发布的朋友圈内容
     * @param memberPostsParam
     * @return
     * @throws ApplicationException
     */
    @DubboxMethod("showNewsfeedsBySn")
    CommonPagedResult<PostInfoResult> getFriendsFeedsList(GetMemberPostsParam memberPostsParam) throws ApplicationException;
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
