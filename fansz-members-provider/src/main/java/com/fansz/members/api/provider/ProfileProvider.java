package com.fansz.members.api.provider;

import com.fansz.members.api.ProfileApi;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.*;
import com.fansz.members.model.search.SearchMemberParam;
import com.fansz.members.model.search.SearchParam;
import com.fansz.members.tools.Constants;
import com.fansz.members.tools.StringTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置服务提供者
 * TODO:后续需要进行重构,统一处理过程,分为权限控制,参数检查,调用服务等
 */
@Component("profileProvider")
public class ProfileProvider extends AbstractProvider implements ProfileApi {

    @Autowired
    private ProfileService profileService;

    /**
     * 获取当前登陆用户的详细信息
     *
     * @param queryUserParam
     * @return
     */
    @Override
    public CommonResult<UserInfoResult> getProfile(QueryProfileParam queryUserParam) {
        UserInfoResult userInfoResult = profileService.getProfile(queryUserParam);
        return renderSuccess(userInfoResult);
    }

    @Override
    public CommonPagedResult<UserInfoResult> searchMembersByKey(SearchParam searchParam) {
        PageBounds pageBounds = new PageBounds(searchParam.getOffset(), searchParam.getLimit());
        PageList<UserInfoResult> data = profileService.searchMembers(searchParam.getSearchVal(), searchParam.getMemberSn(), pageBounds);
        return renderPagedSuccess(data);
    }

    @Override
    public CommonPagedResult<UserInfoResult> searchMembersByType(SearchMemberParam searchMemberParam) {
        PageList<UserInfoResult> data = profileService.searchMembers(searchMemberParam);
        return renderPagedSuccess(data);
    }

    /**
     * 修改当前用户的配置信息
     *
     * @param modifyProfileParam
     * @return
     */
    @Override
    public CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam) {
        profileService.modifyProfile(modifyProfileParam);
        return renderSuccess();
    }

    /**
     * 设置会员类别
     *
     * @param modifyProfileParam
     * @return
     */
    public CommonResult<NullResult> setMemberType(SetMemberParam modifyProfileParam) {
        profileService.setMemberType(modifyProfileParam);
        return renderSuccess();
    }

    /**
     * 校验用户的nickName是否唯一
     *
     * @param modifyProfileParam
     * @return
     */
    @Override
    public CommonResult<ProfileValidateResult> validateNickName(ModifyProfileParam modifyProfileParam) {
        int total = profileService.isExistsNickname(modifyProfileParam.getNickname(), modifyProfileParam.getSn());
        ProfileValidateResult result = new ProfileValidateResult();
        result.setUnique(total > 0 ? false : true);
        return renderSuccess(result);
    }

    @Override
    public CommonPagedResult<ContactInfoResult> getContactInfo(ContactQueryParam contractQueryParam) {
        PageList<ContactInfoResult> dataResult = profileService.findRelationByMobiles(contractQueryParam);
        return super.renderPagedSuccess(dataResult);
    }


    @Override
    public CommonResult<List<String>> getMembersAlbum(ContactQueryParam contractQueryParam) {
        List<String> images = profileService.getImages(contractQueryParam);
        return super.renderSuccess(images);
    }
}