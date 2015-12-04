package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.relationship.*;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.relationship.FriendsQueryParam;

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
    CommonResult<List<FandomInfoResult>> getMemberFandoms(MemberFandomQueryParam fandomParam);

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

    /**
     * 加入fandom
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/join")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam);

    /**
     * 退出fandom
     * @param exitFandomParam
     * @return
     */
    @POST
    @Path("/fandom/exit")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam);


    /**
     * 获取添加我的好友请求列表
     * @return resp 返回对象
     */
    @POST
    @Path("/follower")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult getFriendRequesters(FriendsQueryParam param);

    /**
     * 获取所有候选人接口
     * @return resp 返回对象
     */
    @POST
    @Path("/requester")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult getRequesters(FriendsQueryParam param);

    /**
     * 获取所有候选人接口
     * @return resp 返回对象
     */
    @POST
    @Path("/sprcial")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult getSpecialFriend(FriendsQueryParam param);
}