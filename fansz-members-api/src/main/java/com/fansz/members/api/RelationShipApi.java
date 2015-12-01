package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.AddFriendParam;
import com.fansz.members.model.relationship.FavoriteFandomParam;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.FriendsQueryParam;
import com.fansz.members.model.relationship.FriendInfoResult;
import com.fansz.members.model.relationship.OpRequestParam;

import javax.ws.rs.*;
import java.util.List;

/**
 * 关系服务
 */
@Path("/relationships")
public interface RelationShipApi {
    /**
     * 获取用户关注的fandoms
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/fandoms")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<List<FandomInfoResult>> getFandoms(FavoriteFandomParam fandomParam);

    /**
     * 获取用户的好友列表
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/friends/show")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonPagedResult<List<FriendInfoResult>> getFriends(FriendsQueryParam friendsParam);

    /**
     * 请求添加为好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/friends/add")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam);

    /**
     * 添加为特别好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/specialfriends/add")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 取消特别好友
     *
     * @param addFriendParam
     * @return
     */
    @POST
    @Path("/specialfriends/cancel")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 同意添加好友
     *
     * @param opRequestParam
     * @return
     */
    @POST
    @Path("/friends/agree")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam);

}
