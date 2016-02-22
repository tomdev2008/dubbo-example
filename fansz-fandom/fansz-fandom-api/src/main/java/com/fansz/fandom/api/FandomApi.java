package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.fandom.*;
import com.fansz.fandom.model.profile.UserInfoResult;
import com.fansz.fandom.model.relationship.ExitFandomParam;
import com.fansz.fandom.model.relationship.JoinFandomsParam;
import com.fansz.fandom.model.relationship.MemberFandomQueryParam;

import java.util.List;
import java.util.Map;

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
    CommonResult<FandomInfoResult> addFandom(AddFandomParam addFandomParam) throws ApplicationException;

    /**
     * 关注fandom
     *
     * @param addFandomParam
     * @return
     */
    @DubboxMethod("addJoinFandom")
    CommonResult<FandomInfoResult> addJoinFandom(AddFandomParam addFandomParam) throws ApplicationException;

    /**
     * 加入fandom
     *
     * @param joinFandomsParam
     * @return
     */
    @DubboxMethod("joinFandom")
    CommonResult<NullResult> joinFandom(JoinFandomsParam joinFandomsParam) throws ApplicationException;

    /**
     * 退出fandom
     *
     * @param exitFandomParam
     * @return
     */
    @DubboxMethod("exitFandom")
    CommonResult<NullResult> exitFandom(ExitFandomParam exitFandomParam) throws ApplicationException;

    /**
     * 获取用户关注的fandoms列表
     *
     * @return resp 返回对象
     */
    @DubboxMethod("getMyfandoms")
    CommonPagedResult<FandomInfoResult> getMyFandoms(MemberFandomQueryParam fandomParam) throws ApplicationException;

    /**
     * F010:查询所有的fandom
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @DubboxMethod("listAllFandoms")
    CommonPagedResult<FandomInfoResult> listAllFandoms(FandomQueryParam fandomQueryParam) throws ApplicationException;

    /**
     * F040:查询所有的fandom(排除已经关注的fandom)
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @DubboxMethod("listUnFocusFandoms")
    CommonPagedResult<FandomInfoResult> listUnFocusFandoms(FandomQueryParam fandomQueryParam) throws ApplicationException;

    /**
     * 获取推荐的fandom接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomInfoResult>> 返回对象
     */
    @DubboxMethod("getRecommendFandom")
    CommonPagedResult<FandomInfoResult> getRecommendFandom(FandomQueryParam fandomQueryParam) throws ApplicationException;

    /**
     * 获取圈子fandom分类接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<Map<String, String>>> 返回对象
     */
    @DubboxMethod("getFandomCategory")
    CommonResult<List<Map<String, Object>>> getFandomCategory(FandomQueryParam fandomQueryParam) throws ApplicationException;

    /**
     * 获取圈子成员列表接口
     *
     * @param fandomQueryParam 查询参数
     * @return CommonResult<List<FandomCategory>> 返回对象
     */
    @DubboxMethod("getFandomMembers")
    CommonPagedResult<UserInfoResult> getFandomMembers(FandomQueryParam fandomQueryParam) throws ApplicationException;

    /* 获取圈子信息接口
     *
     * @param fandomInfoParam 圈子id
     * @return resp 返回对象
     */
    @DubboxMethod("getFandomInfo")
    CommonResult<FandomInfoResult> getFandom(FandomInfoParam fandomInfoParam) throws ApplicationException;


    /**
     * 根据关键字搜索fandom
     *
     * @param searchFandomParam
     * @return
     */
    @DubboxMethod("searchFandoms")
    CommonPagedResult<FandomInfoResult> searchFandoms(SearchFandomParam searchFandomParam) throws ApplicationException;

    /**
     * 删除fandom
     *
     * @param delFandomParam
     * @return
     */
    @DubboxMethod("deleteFandom")
    CommonResult<NullResult> delFandom(DelFandomParam delFandomParam) throws ApplicationException;

    /**
     * 修改fandom信息
     *
     * @param modifyFandomParam
     * @return
     */
    @DubboxMethod("modifyFandom")
    CommonResult<FandomInfoResult> modifyFandom(ModifyFandomParam modifyFandomParam) throws ApplicationException;
}
