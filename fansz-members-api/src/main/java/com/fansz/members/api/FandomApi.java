package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.fandom.FandomFollowers;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.fandom.FandomQueryParam;
import com.fansz.members.model.fandom.PostsQueryParam;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.fandom.FandomParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * fandom服务
 */
@Path("/fandoms")
public interface FandomApi {

    /**
     * 添加圈子接口
     *
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
     *
     * @param fandomQueryParam 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/{id}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<FandomInfoResult> getFandom(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子里面的帖子信息接口
     *
     * @param param 圈子
     * @return resp 返回对象
     */
    @GET
    @Path("/posts")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<PostInfoResult> getPostsByFandom(PostsQueryParam param);

    /**
     * 分类获取圈子接口
     *
     * @param categoryId 圈子id
     * @return resp 返回对象
     */
    @GET
    @Path("/category/{categoryId}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FandomInfoResult>> getFandomsByCategory(@PathParam("categoryId") String categoryId);

    /**
     * 关注圈子接口
     *
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follow")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response followFandom(@PathParam("id") String id);

    /**
     * 取消关注圈子接口
     *
     * @param id 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/unfollow")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response unfollowFandom(@PathParam("id") String id);

    /**
     * 获取推荐圈子信息接口
     *
     * @return resp 返回对象
     */
    @GET
    @Path("/recommend")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response getRecommendFandom();

    /**
     * 获取所有圈子类别接口
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/category/get/{id}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response getSubCategory(@PathParam("id") String id);

    /**
     * 获取所有圈类别接口
     *
     * @return resp 返回对象
     */
    @GET
    @Path("/category")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response getCategory();

    /**
     * 获取圈子类别的所有fandom接口
     *
     * @param id 圈子子类别id
     * @return resp 返回对象
     */
    @POST
    @Path("/subcategory/{id}")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response getFandomsBySubCategory(@PathParam("id") String id);

    /**
     * 获取圈子所有关注人接口
     *
     * @param fandomFollowers 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/follower")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public Response followerOfFandom(FandomFollowers fandomFollowers);
}
