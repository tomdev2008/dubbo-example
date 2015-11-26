package com.fansz.members.api;

import com.fansz.members.model.param.PagePara;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by root on 15-11-26.
 */
public interface PostApi {

    /**
     * 发帖子接口
     * @param form 帖子信息
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response addPost(InputStream form);

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
     * @param id 帖子id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/like")
    @Produces("application/json")
    public Response likePost(@PathParam("id") String id);

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
    public Response getFriendsPosts(PagePara pagePara);

    /**
     * 获得我所关注的所有fandom的所有帖子接口
     * @param pagePara 分页参数
     * @return resp 返回对象
     */
    @POST
    @Path("/fandom")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllFandomPosts(PagePara pagePara);
}
