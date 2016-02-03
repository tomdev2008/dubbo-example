package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.profile.*;
import com.fansz.fandom.model.search.SearchMemberParam;

import java.util.List;
import java.util.Map;

/**
 * 用户服务
 */
@DubboxService("profiles")
public interface ProfileApi {
    /**
     * 查询用户的详细信息
     *
     * @param queryUserParam
     * @return
     */
    @DubboxMethod("getProfile")
    CommonResult<Map<String,String>> getProfile(QueryProfileParam queryUserParam) throws ApplicationException;

    /**
     * 修改会员信息
     *
     * @param modifyProfileParam
     * @return
     */
    @DubboxMethod("modifyMyProfile")
    CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam) throws ApplicationException;

    /**
     * C022:校验用户的nickName是否唯一
     *
     * @param nicknameCheckParam
     * @return
     */
    @DubboxMethod("validateNickname")
    CommonResult<ProfileValidateResult> validateNickName(NicknameCheckParam nicknameCheckParam) throws ApplicationException;

    @DubboxMethod("setMemberType")
    CommonResult<NullResult> setMemberType(SetMemberParam modifyProfileParam) throws ApplicationException;

    /**
     * 查询用户相册
     */
    @Deprecated
    @DubboxMethod("getMembersAlbum")
    CommonResult<List<String>> getMembersAlbum(ContactQueryParam contractQueryParam) throws ApplicationException;
    /**
     * 根据昵称、账号、手机号码搜索会员
     *
     * @param searchMemberParam
     * @return
     */
    @DubboxMethod("searchMembers")
    CommonPagedResult<UserInfoResult> searchMembersByKey(SearchMemberParam searchMemberParam) throws ApplicationException;

    /**
     * C021:获取某类型（名人、明星、名企等）的会员列表
     * @param searchMemberParam
     * @return
     */
    @DubboxMethod("getFamousers")
    CommonPagedResult<UserInfoResult> searchMembersByType(SearchMemberParam searchMemberParam) throws ApplicationException;


}
