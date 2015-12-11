package com.fansz.members.api;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.fandom.*;
import com.fansz.members.model.relationship.ExitFandomParam;
import com.fansz.members.model.relationship.JoinFandomParam;
import com.fansz.members.model.relationship.MemberFandomQueryParam;
import com.fansz.members.model.profile.ContactInfoResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * fandom服务
 */
@Path("/fandoms")
@Consumes(ContentType.APPLICATION_JSON_UTF_8)
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public interface FandomApi {

    /**
     * 创建Fandom
     *
     * @param addFandomParam
     * @return
     */
    @POST
    @Path("/addFandom")
    CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam);

    /**
     * 关注fandom
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/addJoin")
    CommonResult<NullResult> addJoinFandom(JoinFandomParam joinFandomParam);

    /**
     * 加入fandom
     *
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/join")
    CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam);

    /**
     * 特殊关注fandom
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/beSpecial")
    CommonResult<NullResult> markSpecialFandom(JoinFandomParam joinFandomParam);

    /**
     * 取消特别关注fandom
     * @param joinFandomParam
     * @return
     */
    @POST
    @Path("/fandom/notSpecial")
    CommonResult<NullResult> removeSpecialFandom(JoinFandomParam joinFandomParam);

    /**
     * 退出fandom
     *
     * @param exitFandomParam
     * @return
     */
    @POST
    @Path("/fandom/exit")
    CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam);

    /**
     * 获取用户关注的fandoms列表
     *
     * @return resp 返回对象
     */
    @POST
    @Path("/myFandoms")
    CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam);

    /**
     * 查询所有的fandom
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @POST
    @Path("/list")
    CommonResult<List<FandomInfoResult>> listAllFandoms(FandomQueryParam fandomQueryParam);


    /**
     * 获取推荐的fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @POST
    @Path("/recommend")
    CommonPagedResult<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子fandom分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @POST
    @Path("/category")
    CommonResult<List<FandomCategorys>> getFandomCategory(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子成员列表接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategorys>> 返回对象
     */
    @POST
    @Path("/members")
    CommonPagedResult<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);

    /* 获取圈子信息接口
     *
     * @param fandomInfoParam 圈子id
     * @return resp 返回对象
     */
    @POST
    @Path("/info")
    CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam);



    @POST
    @Path("/fandom/search")
    CommonPagedResult<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam);


}
