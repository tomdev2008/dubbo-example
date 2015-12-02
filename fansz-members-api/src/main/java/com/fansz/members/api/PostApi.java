package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.PageParam;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.post.PostLikeInfoResult;
import com.fansz.members.model.post.PostParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by root on 15-11-26.
 */
@Path("/posts")
public interface PostApi {

    /**
     * 发帖子接口
     * @param postParam 帖子信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response addPost(PostParam postParam);

    /**
     * 删除帖子接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/remove")
    @Produces("application/json")
    public Response removePost(@PathParam("id") String id);

    /**
     * 获取帖子信息接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPost(@PathParam("id") String id);


    /**
     * 帖子点赞接口
     * @return resp 返回对象
     */
    @POST
    @Path("/like")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<PostLikeInfoResult>> likePost(PostParam postParam);

    /**
     * 取消帖子点赞接口
     * @param id 帖子id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/unlike")
    @Produces("application/json")
    public Response unlikePost(@PathParam("id") String id);

    /**
     * 获得好友的所有帖子接口
     * @param friendId 好友id
     * @return resp 返回对象
     */
    @GET
    @Path("/friend/{id}")
    @Produces("application/json")
    public Response getFriendPosts(@PathParam("id") String friendId);

    /**
     * 获得所有好友的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/friend")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendsPosts(PageParam pagePara);

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/fandom")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllFandomPosts(PageParam pagePara);
}
