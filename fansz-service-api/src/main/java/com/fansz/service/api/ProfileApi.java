package com.fansz.service.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.extension.DubboxService;
import com.fansz.service.model.profile.*;
import com.fansz.service.model.search.SearchMemberParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
     * @param searchMemberParam
     * @return
     */
    @Path("/searchByKey")
    @POST
    @DubboxService("searchMembers")
    CommonPagedResult<UserInfoResult> searchMembersByKey(SearchMemberParam searchMemberParam);


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
