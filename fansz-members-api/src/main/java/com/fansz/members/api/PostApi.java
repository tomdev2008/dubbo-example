package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.extension.DubboxService;
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
    @DubboxService("publishPost")
    CommonResult<GetPostInfoResult> addPost(AddPostParam addPostParam);

    /**
     * 删除帖子接口
     *
     * @param removePostrParam 帖子id
     * @return resp 返回对象
     */
    @POST
    @Path("/deletePost")
    @DubboxService("deletePost")
    CommonResult<NullResult> removePost(RemovePostParam removePostrParam);

    /**
     * 根据postId获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    @POST
    @Path("/get")
    @DubboxService("getPost")
    CommonResult<GetPostInfoResult> getPost(GetPostByIdParam postParam);


    /**
     * 帖子点赞接口
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/like")
    @DubboxService("votePost")
    CommonResult<NullResult> addLike(AddLikeParam addLikeParam);

    /**
     * 取消帖子点赞接口
     *
     * @param deleteLikeParam
     * @return resp 返回对象
     */
    @POST
    @Path("/deleteLike")
    @DubboxService("deleteVote")
    CommonResult<NullResult> deleteLike(DeleteLikeParam deleteLikeParam);

    /**
     * 查询POST的点赞信息
     *
     * @param postParam
     * @return
     */
    @POST
    @Path("/listLike")
    @DubboxService("listPostVoters")
    CommonResult<List<PostLikeInfoResult>> listPostVoteList(PostParam postParam);

    /**
     * 获得所有好友的所有帖子接口
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/friends")
    @DubboxService("listMyNewsfeeds")
    CommonPagedResult<PostInfoResult> listFriendsPosts(GetPostsParam getPostsParam);

    /**
     * 获得我所关注的所有fandom的帖子列表
     *
     * @param getPostsParam 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/myFandoms")
    @DubboxService("listMyFandomsPost")
    CommonPagedResult<PostInfoResult> listMyFandomPosts(GetPostsParam getPostsParam);


    /**
     * 根据关键字查询帖子
     *
     * @param searchPostParam
     * @return
     */
    @POST
    @Path("/searchPosts")
    @DubboxService("searchPosts")
    CommonPagedResult<SearchPostResult> searchPosts(SearchPostParam searchPostParam);

    /**
     * 查询某人在某个fandom的所有帖子列表
     *
     * @param getMemberFandomPostsParam
     * @return
     */
    @POST
    @Path("/memberPosts")
    @DubboxService("listPostInFandom")
    CommonPagedResult<PostInfoResult> getMemberPostsByFandom(GetMemberFandomPostsParam getMemberFandomPostsParam);


    /**
     * 获取某个fandom的帖子列表
     *
     * @param param 圈子
     * @return resp 返回对象
     */
    @POST
    @Path("/fandom")
    @DubboxService("listFandomPosts")
    CommonPagedResult<PostInfoResult> getPostsByFandom(PostsQueryParam param);


    /**
     * 获取某人所有帖子列表
     *
     * @param postParam
     * @return resp 返回对象
     */
    @POST
    @Path("/memberPostsAll")
    @DubboxService("getMemberAllPosts")
    CommonPagedResult<PostInfoResult> getAllPostsByMember(PostParam postParam);


}
