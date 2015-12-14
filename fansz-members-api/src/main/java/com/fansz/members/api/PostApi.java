package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.post.PostsQueryParam;
import com.fansz.members.model.post.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by root on 15-11-26.
 */
@Path("/posts")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface PostApi {

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    CommonResult<GetPostInfoResult> addPost(AddPostParam addPostParam);

    /**
     * 删除帖子接口
     *
     * @param removePostrParam 帖子id
     * @return resp 返回对象
     */
    @POST
    @Path("/deletePost")
    CommonResult<NullResult> removePost(RemovePostParam removePostrParam);

    /**
     * 根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @POST
    @Path("/get")
    CommonResult<GetPostInfoResult> getPost(GetPostByIdParam postParam);


    /**
     * 帖子点赞接口
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/like")
    CommonResult<NullResult> addLike(AddLikeParam addLikeParam);

    /**
     * 取消帖子点赞接口
     *
     * @param deleteLikeParam
     * @return resp 返回对象
     */
    @POST
    @Path("/deleteLike")
    CommonResult<NullResult> deleteLike(DeleteLikeParam deleteLikeParam);

    /**
     * 查询POST的点赞信息
     *
     * @param postParam
     * @return
     */
    @POST
    @Path("/listLike")
    CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam);

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/friends")
    CommonPagedResult<PostInfoResult> listFriendsPosts(GetPostsParam getPostsParam);

    /**
     * 获得我所关注的所有fandom的帖子列表
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/myFandoms")
    CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam);


    /**
     * 根据关键字查询帖子
     *
     * @param searchPostParam
     * @return
     */
    @POST
    @Path("/searchPosts")
    CommonPagedResult<SearchPostResult> searchPosts(SearchPostParam searchPostParam);

    /**
     * 查询某人在某个fandom的所有帖子列表
     *
     * @param getMemberFandomPostsParam
     * @return
     */
    @POST
    @Path("/memberPosts")
    CommonPagedResult<PostInfoResult> getMemberPostsByFandom(GetMemberFandomPostsParam getMemberFandomPostsParam);


    /**
     * 获取某个fandom的帖子列表
     *
     * @param param 圈子
     * @return resp 返回对象
     */
    @POST
    @Path("/fandom")
    CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param);


    /**
     * 获取某人所有帖子列表
     *
     * @param postParam
     * @return resp 返回对象
     */
    @POST
    @Path("/memberPostsAll")
    CommonPagedResult<PostInfoResult> getAllPostsByMember(PostParam postParam);


}
