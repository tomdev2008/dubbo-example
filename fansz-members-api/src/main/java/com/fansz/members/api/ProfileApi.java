package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.extension.DubboxService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.*;
import com.fansz.members.model.search.SearchMemberParam;
import com.fansz.members.model.search.SearchParam;

import javax.ws.rs.*;
import java.util.List;

/**
 * 用户服务
 */
@Path("/profiles")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface ProfileApi {
    /**
     * 查询用户的详细信息
     *
     * @param queryUserParam
     * @return
     */
    @Path("/show")
    @POST
    @DubboxService("getProfile")
    CommonResult<UserInfoResult> getProfile(QueryProfileParam queryUserParam);

    /**
     * 根据昵称、账号、手机号码搜索会员
     *
     * @param searchParam
     * @return
     */
    @Path("/searchByKey")
    @POST
    @DubboxService("searchMembers")
    CommonPagedResult<UserInfoResult> searchMembersByKey(SearchParam searchParam);


    @Path("/searchByType")
    @POST
    @DubboxService("getFamousers")
    CommonPagedResult<UserInfoResult> searchMembersByType(SearchMemberParam searchMemberParam);

    /**
     * 修改会员信息
     *
     * @param modifyProfileParam
     * @return
     */
    @POST
    @Path("/change")
    @DubboxService("modifyMyProfile")
    CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam);

    /**
     * 校验用户的nickName是否唯一
     *
     * @param nicknameCheckParam
     * @return
     */
    @POST
    @Path("/validate")
    @DubboxService("validateNickname")
    CommonResult<ProfileValidateResult> validateNickName(NicknameCheckParam nicknameCheckParam);

    @POST
    @Path("/setMemberType")
    @DubboxService("setMemberType")
    CommonResult<NullResult> setMemberType(SetMemberParam modifyProfileParam);

    /**
     * 查询用户相册
     */
    @POST
    @Path("/album")
    @DubboxService("getMembersAlbum")
    CommonResult<List<String>> getMembersAlbum(ContactQueryParam contractQueryParam);

    /**
     * 上传用户通讯录，搜索出通讯录好友（包含好友状态）
     */
    @POST
    @Path("/contacts/match")
    @DubboxService("searchContacts")
    CommonPagedResult<ContactInfoResult> getContactInfo(ContactQueryParam contractQueryParam);


}
