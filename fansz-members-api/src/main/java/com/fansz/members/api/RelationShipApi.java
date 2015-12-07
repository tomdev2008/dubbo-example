package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.*;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.relationship.FriendsQueryParam;

import javax.ws.rs.*;
import java.util.List;

/**
 * 关系服务
 */
@Path("/relationships")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface RelationShipApi {


    /**
     * 获取用户的好友列表
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/friends/show")
    CommonPagedResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam);

    /**
     * 获取特别用户好友列表
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/specialFriends/show")
    CommonPagedResult<FriendInfoResult> getSpecialFriends(FriendsQueryParam friendsParam);

    /**
     * 请求添加为好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/friends/add")
    CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam);

    /**
     * 添加为特别好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/specialfriends/add")
    CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 取消特别好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/specialfriends/cancel")
    CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 同意添加好友
     *
     * @param opRequestParam
     * @return
     */
    @POST
    @Path("/friends/agree")
    CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam);


    /**
     * 获取添加我的好友请求
     *
     * @param friendsQueryParam
     * @return
     */
    @POST
    @Path("/friends/requestMe")
    CommonPagedResult<FriendInfoResult> getFriendRquests(FriendsQueryParam friendsQueryParam);

    /**
     * 获取我发出的好友请求列表
     *
     * @param friendsQueryParam
     * @return
     */
    @POST
    @Path("/friends/myRequest")
    CommonPagedResult<FriendInfoResult> getRequesters(FriendsQueryParam friendsQueryParam);
}