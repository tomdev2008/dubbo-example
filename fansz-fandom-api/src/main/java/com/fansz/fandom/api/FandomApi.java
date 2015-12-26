package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomParam;
import com.fansz.fandom.model.relationship.MemberFandomQueryParam;

import java.util.List;

/**
 * fandom服务
 */
@DubboxService("fandoms")
public interface FandomApi {

    /**
     * 创建Fandom
     *
     * @param addFandomParam
     * @return
     */
    @DubboxMethod("createFandom")
    CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam);

    /**
     * 关注fandom
     *
     * @param addFandomParam
     * @return
     */
    @DubboxMethod("addJoinFandom")
    CommonResult<FandomInfoResult> addJoinFandom(AddFandomParam addFandomParam);

    /**
     * 加入fandom
     *
     * @param joinFandomParam
     * @return
     */
    @DubboxMethod("joinFandom")
    CommonResult<NullResult> joinFandom(JoinFandomParam joinFandomParam);

    /**
     * 特殊关注fandom
     * @param joinFandomParam
     * @return
     */
    // @POST
    // @Path("/fandom/beSpecial")
    //@DubboxMethod("beMySpecialFandom")
    //CommonResult<NullResult> markSpecialFandom(JoinFandomParam joinFandomParam);

    /**
     * 取消特别关注fandom
     * @param joinFandomParam
     * @return
     */
    // @POST
    // @Path("/fandom/notSpecial")
    // @DubboxMethod("removeMySpecialFandom")
    //CommonResult<NullResult> removeSpecialFandom(JoinFandomParam joinFandomParam);

    /**
     * 退出fandom
     *
     * @param exitFandomParam
     * @return
     */
    @DubboxMethod("exitFandom")
    CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam);

    /**
     * 获取用户关注的fandoms列表
     *
     * @return resp 返回对象
     */
    @DubboxMethod("getMyfandoms")
    CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam);

    /**
     * 查询所有的fandom
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @DubboxMethod("listAllFandoms")
    CommonResult<List<FandomInfoResult>> listAllFandoms(FandomQueryParam fandomQueryParam);


    /**
     * 获取推荐的fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @DubboxMethod("getRecommendFandom")
    CommonPagedResult<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子fandom分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategory>> 返回对象
     */
    @DubboxMethod("getFandomCategory")
    CommonResult<List<FandomCategory>> getFandomCategory(FandomQueryParam fandomQueryParam);

    /**
     * 获取圈子成员列表接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategory>> 返回对象
     */
    @DubboxMethod("getFandomMembers")
    CommonPagedResult<ContactInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam);

    /* 获取圈子信息接口
     *
     * @param fandomInfoParam 圈子id
     * @return resp 返回对象
     */
    @DubboxMethod("getFandomInfo")
    CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam);


    /**
     * 根据关键字搜索fandom
     *
     * @param searchFandomParam
     * @return
     */
    @DubboxMethod("searchFandoms")
    CommonPagedResult<SearchFandomResult> searchFandoms(SearchFandomParam searchFandomParam);

    /**
     * 删除fandom
     *
     * @param delFandomParam
     * @return
     */
    @DubboxMethod("deleteFandom")
    CommonResult<NullResult> delFandom(DelFandomParam delFandomParam);

    /**
     * 修改fandom信息
     *
     * @param modifyFandomParam
     * @return
     */
    @DubboxMethod("modifyFandom")
    CommonResult<FandomInfoResult> modifyFandom(ModifyFandomParam modifyFandomParam);
}
