package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.ContactQueryParam;
import com.fansz.members.model.relationship.FriendsQueryParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by root on 15-11-26.
 */
@Path("/contacts")
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
    @Path("/profile/{id}")
    @Produces("application/json")
    public Response getUserProfile(@PathParam("id") String id);


    /**
     * 上传用户通讯录，搜索出通讯录好友（包含好友状态）
     */
    @POST
    @Path("/album")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<List<String>> getMembersAlbum(ContactQueryParam contractQueryParam);
}
