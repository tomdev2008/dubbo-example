package com.fansz.members.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
public interface ContactsApi {

    /**
     * 添加好友接口
     * @param followId 好友用户
     * @return resp 返回对象
     */
    @POST
    @Path("/friend/add/{followId}")
    @Produces("application/json")
    public Response addFriend(@PathParam("followId") String followId);

    /**
     * 接受添加接口
     * @param followId 好友用户id
     * @return resp 返回对象
     */
    @POST
    @Path("/friend/accept/{followId}")
    @Produces("application/json")
    public Response acceptFriend(@PathParam("followId") String followId);

    /**
     * 删除好友接口
     * @param id 好友用户id
     * @return resp 返回对象
     */
    @POST
    @Path("/friend/remove/{id}")
    @Produces("application/json")
    public Response removeFriend(@PathParam("id") String id);

    /**
     * 获取我所有好友接口
     * @return resp 返回对象
     */
    @GET
    @Path("/friend")
    @Produces("application/json")
    public Response getFriends();

    /**
     * 获取好友详细信息接口
     * @param id 好友用户id
     * @return resp 返回对象
     */
    @GET
    @Path("/friend/{id}")
    @Produces("application/json")
    public Response getFriendProfile(@PathParam("id") String id);

    /**
     * 搜索好友接口
     * @param criteria 查询条件
     * @return resp 返回对象
     */
    @POST
    @Path("/findnew")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findFriend(String criteria);


    /**
     * 获取用户详细信息接口
     * @param id 用户id
     * @return resp 返回对象
     */
    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Response getUserProfile(@PathParam("id") String id);

    /**
     * 获取所有候选人接口
     * @return resp 返回对象
     */
    @GET
    @Path("/follower")
    @Produces("application/json")
    public Response getFollowRequest();
}
