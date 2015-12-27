package com.fansz.relations.api;


import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.relations.model.AddFriendParam;
import com.fansz.relations.model.FriendInfoResult;
import com.fansz.relations.model.FriendsQueryParam;
import com.fansz.relations.model.OpRequestParam;

/**
 * 关系服务
 */
@DubboxService("relations")
public interface RelationShipApi {


    /**
     * 获取用户的好友列表
     *
     * @return resp 返回对象
     */
    @DubboxMethod("getFriends")
    CommonPagedResult<FriendInfoResult> getFriends(FriendsQueryParam friendsParam);

    /**
     * 获取特别用户好友列表
     *
     * @return resp 返回对象
     */
    @DubboxMethod("getSpecialFriend")
    CommonPagedResult<FriendInfoResult> getSpecialFriends(FriendsQueryParam friendsParam);

    /**
     * 请求添加为好友
     *
     * @param addFriendParam
     * @return
     */
    @DubboxMethod("requestToBeFriends")
    CommonResult<NullResult> addFriendRequest(AddFriendParam addFriendParam);

    /**
     * 添加为特别好友
     *
     * @param addFriendParam
     * @return
     */
    @DubboxMethod("beMySpecialFriend")
    CommonResult<NullResult> addSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 取消特别好友
     *
     * @param addFriendParam
     * @return
     */
    @DubboxMethod("removeMySpecialFriend")
    CommonResult<NullResult> cancelSpecialFriend(AddFriendParam addFriendParam);

    /**
     * 同意添加好友
     *
     * @param opRequestParam
     * @return
     */
    @DubboxMethod("agreeAddRequest")
    CommonResult<NullResult> agreeRequest(OpRequestParam opRequestParam);


    /**
     * 获取添加我的好友请求
     *
     * @param friendsQueryParam
     * @return
     */
    @DubboxMethod("getFriendRequests")
    CommonPagedResult<FriendInfoResult> getFriendRquests(FriendsQueryParam friendsQueryParam);

    /**
     * 获取我发出的好友请求列表
     *
     * @param friendsQueryParam
     * @return
     */
    @DubboxMethod("getRequesters")
    CommonPagedResult<FriendInfoResult> getRequesters(FriendsQueryParam friendsQueryParam);
}