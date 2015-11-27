package com.fansz.members.api;

import com.fansz.members.model.param.ModifyProfilePara;
import com.fansz.members.model.param.UsersInfoParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by root on 15-11-26.
 */
@Path("/profile")
public interface Profile {

    @GET
    @Produces("application/json")
    public Response getProfile();

    @POST
    @Path("/modify")
    @Consumes("application/json")
    @Produces("application/json")
    public Response modifyProfile(ModifyProfilePara modifyProfilePara);

    /**
     * 获取我所有关注的fandom接口
     * @param avater 个人头像信息
     * @return resp 返回对象
     */
    @POST
    @Path("/avatar")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public Response setAvatar(String avater);

    /**
     * 获取我所有关注的fandom接口
     * @return resp 返回对象
     */
    @GET
    @Path("/fandom")
    @Produces("application/json")
    public Response getFollowedFandom();

    /**
     * 获得我的好友信息
     * @return resp 返回对象
     */
    @POST
    @Path("/get/userInfo")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUsers(UsersInfoParam usersInfoParam);
}
