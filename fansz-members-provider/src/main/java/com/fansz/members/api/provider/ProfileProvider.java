package com.fansz.members.api.provider;

import com.fansz.members.api.ProfileApi;
import com.fansz.members.api.service.ProfileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.profile.ModifyProfileParam;
import com.fansz.members.model.profile.QueryProfileParam;
import com.fansz.members.model.profile.UserInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配置服务提供者
 */
@Component("profileProvider")
public class ProfileProvider implements ProfileApi {


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
        try {
            UserInfoResult userInfoResult = profileService.getProfile(queryUserParam.getUid());
            result.setResult(userInfoResult);
        } catch (Exception iae) {
            result.setStatus(Constants.FAIL);
        }
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
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus(Constants.SUCCESS);
        try {
            profileService.modifyProfile(modifyProfileParam);
        } catch (Exception e) {
            result.setStatus(Constants.FAIL);
        }
        return result;
    }

}
