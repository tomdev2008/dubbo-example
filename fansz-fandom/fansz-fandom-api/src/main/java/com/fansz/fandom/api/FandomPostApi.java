package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.post.*;

import java.util.List;

/**
 * Created by root on 15-11-26.
 */
@DubboxService("fandomPosts")
public interface FandomPostApi {
    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @DubboxMethod("publishPost")
    CommonResult<PostInfoResult> addPost(AddPostParam addPostParam) throws ApplicationException;

    /**
     * 删除帖子接口
     *
     * @param removePostrParam 帖子id
     * @return resp 返回对象
     */
    @DubboxMethod("deletePost")
    CommonResult<NullResult> removePost(RemovePostParam removePostrParam) throws ApplicationException;

    /**
     * F025:根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @DubboxMethod("getPost")
    CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam) throws ApplicationException;


    /**
     * 帖子点赞接口
     *
     * @return resp 返回对象
     */
    @DubboxMethod("votePost")
    CommonResult<NullResult> addLike(AddLikeParam addLikeParam) throws ApplicationException;

    /**
     * 取消帖子点赞接口
     *
     * @param deleteLikeParam
     * @return resp 返回对象
     */
    @DubboxMethod("deleteVote")
    CommonResult<NullResult> deleteLike(DeleteLikeParam deleteLikeParam) throws ApplicationException;

    /**
     * 查询POST的点赞信息
     *
     * @param postParam
     * @return
     */
    @DubboxMethod("listPostVoters")
    CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam) throws ApplicationException;

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @DubboxMethod("listMyNewsfeeds")
    CommonPagedResult<PostInfoResult> listFriendsPosts(GetPostsParam getPostsParam) throws ApplicationException;

    /**
     * 获得我所关注的所有fandom的帖子列表
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @DubboxMethod("listMyFandomsPost")
    CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam) throws ApplicationException;


    /**
     * 根据关键字查询帖子
     *
     * @param searchPostParam
     * @return
     */
    @DubboxMethod("searchPosts")
    CommonPagedResult<PostInfoResult> searchPosts(SearchPostParam searchPostParam) throws ApplicationException;

    /**
     * 查询某人在某个fandom的所有帖子列表
     *
     * @param getMemberFandomPostsParam
     * @return
     */
    @DubboxMethod("listPostInFandom")
    CommonPagedResult<PostInfoResult> getMemberPostsByFandom(GetMemberFandomPostsParam getMemberFandomPostsParam) throws ApplicationException;


    /**
     * 获取某个fandom的帖子列表
     *
     * @param param 圈子
     * @return resp 返回对象
     */
    @DubboxMethod("listFandomPosts")
    CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param) throws ApplicationException;


    /**
     * 获取某人所有帖子列表
     *
     * @param postParam
     * @return resp 返回对象
     */
    @DubboxMethod("getMemberAllPosts")
    CommonPagedResult<PostInfoResult> getAllPostsByMember(GetMemberPostsParam postParam) throws ApplicationException;


}
