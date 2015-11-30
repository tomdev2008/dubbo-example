package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.FavoriteFandomParam;
import com.fansz.members.model.fandom.FandomInfoResult;
import com.fansz.members.model.profile.FriendResult;
import com.fansz.members.model.profile.FriendsParam;

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
    @GET
    @Path("/fandoms")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FandomInfoResult>> getFandoms(FavoriteFandomParam fandomParam);

    /**
     * 获取用户的好友信息
     *
     * @return resp 返回对象
     */
    @GET
    @Path("/friends")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<UserInfoResult>> getFriends(FriendsParam friendsParam);
}
