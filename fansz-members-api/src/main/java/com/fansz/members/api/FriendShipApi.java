package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.friendship.FocusedFandomParam;
import com.fansz.members.model.friendship.FocusedFandomResult;
import com.fansz.members.model.user.FriendResult;
import com.fansz.members.model.user.FriendsParam;

import javax.ws.rs.*;
import java.util.List;

/**
 * 关系服务
 */
public interface FriendShipApi {
    /**
     * 获取用户关注的fandoms
     *
     * @return resp 返回对象
     */
    @GET
    @Path("/fandoms")
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FocusedFandomResult>> getFandoms(FocusedFandomParam fandomParam);

    /**
     * 获取用户的好友信息
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/friends")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    public CommonResult<List<FriendResult>> getFriends(FriendsParam friendsParam);
}
