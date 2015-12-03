package com.fansz.members.api.provider;

import com.fansz.members.api.ProfileApi;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.tools.Constants;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.*;
import com.fansz.members.tools.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配置服务提供者
 * TODO:后续需要进行重构,统一处理过程,分为权限控制,参数检查,调用服务等
 */
@Component("profileProvider")
public class ProfileProvider implements ProfileApi {

    private final static NullResult PRESENCE = new NullResult();

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
        CommonResult<UserInfoResult> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        UserInfoResult userInfoResult = profileService.getProfile(queryUserParam.getUid());
        result.setResult(userInfoResult);
        result.setMessage("Get profile successfully");
        return result;
    }

    /**
     * 修改当前用户的配置信息
     *
     * @param modifyProfileParam
     * @return
     */
    @Override
    public CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam) {
        if (StringTools.isBlank(modifyProfileParam.getAccessToken())) {
            throw new ApplicationException(Constants.PARAMETERS_ERROR, "Param error, see doc for more info");
        }
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        result.setMessage("Change profile successfully");
        profileService.modifyProfile(modifyProfileParam);
        result.setResult(PRESENCE);
        return result;
    }

    @Override
    public CommonResult<UserInfoResult> getProfileByNickname(ModifyProfileParam modifyProfileParam) {
        profileService.getProfileByNickname(modifyProfileParam);
        return null;
    }

    @Override
    public CommonResult<List<ContactInfoResult>> getContactInfo(ContactQueryParam contractQueryParam) {
        CommonResult<List<ContactInfoResult>> result = new CommonResult<>();
        List<ContactInfoResult> dataResult = profileService.findRelationByMobiles(contractQueryParam);
        result.setResult(dataResult);
        result.setStatus(Constants.SUCCESS);
        return result;
    }
}