package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.*;

import javax.ws.rs.*;
import java.util.List;

/**
 * 用户服务
 */
@Path("/profiles")
public interface ProfileApi {
    /**
     * 查询用户的详细信息
     *
     * @param queryUserParam
     * @return
     */
    @Path("/show")
    @POST
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<UserInfoResult> getProfile(QueryProfileParam queryUserParam);

    /**
     * 修改会员信息
     *
     * @param modifyProfileParam
     * @return
     */
    @POST
    @Path("/change")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam);

    @POST
    @Path("/getProfileBykeyword")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonResult<UserInfoResult> getProfileByNickname(ModifyProfileParam modifyProfileParam);


    /**
     * 上传用户通讯录，搜索出通讯录好友（包含好友状态）
     */
    @POST
    @Path("/contact/match")
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    CommonPagedResult<List<ContactInfoResult>> getContactInfo(ContactQueryParam contractQueryParam);
}
