package com.fansz.members.api;

import com.fansz.members.model.param.FandomFollowers;
import com.fansz.members.model.param.FandomParam;
import com.fansz.members.model.param.GetPostsParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
public interface Fandom {

    /**
     * 添加圈子接口
     * @param form 圈子参数
     * @return resp 返回对象
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addFandom(FandomParam form);

    /**
     * 获取圈子信息接口
     * @param id 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getFandom(@PathParam("id") String id);

    /**
     * 获取圈子里面的帖子信息接口
     * @param param 圈子
     * @return resp 返回对象
     */
    @POST
    @Path("/posts")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getPostsByFandom(GetPostsParam param);

    /**
     * 分类获取圈子接口
     * @param categoryId 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/category/{categoryId}")
    @Produces("application/json")
    public Response getFandomsByCategory(@PathParam("categoryId") String categoryId);

    /**
     * 关注圈子接口
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follow/{id}")
    @Produces("application/json")
    public Response followFandom(@PathParam("id") String id);

    /**
     * 取消关注圈子接口
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/{id}/unfollow")
    @Produces("application/json")
    public Response unfollowFandom(@PathParam("id") String id);

    /**
     * 获取推荐圈子信息接口
     * @return resp 返回对象
     */
    @GET
    @Path("/recommend")
    @Produces("application/json")
    public Response getRecommendFandom();

    /**
     * 获取所有圈子类别接口
     * @return resp 返回对象
     */
    @POST
    @Path("/category/get/{id}")
    @Produces("application/json")
    public Response getSubCategory(@PathParam("id") String id);

    /**
     * 获取所有圈类别接口
     * @return resp 返回对象
     */
    @GET
    @Path("/category")
    @Produces("application/json")
    public Response getCategory();

    /**
     * 获取圈子类别的所有fandom接口
     * @param id 圈子子类别id
     * @return resp 返回对象
     */
    @POST
    @Path("/subcategory/get/{id}")
    @Produces("application/json")
    public Response getFandomsBySubCategory(@PathParam("id") String id);

    /**
     * 获取圈子所有关注人接口
     * @param fandomFollowers 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follower")
    @Consumes("application/json")
    @Produces("application/json")
    public Response followerOfFandom(FandomFollowers fandomFollowers);
}
