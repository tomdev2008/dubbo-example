package com.fansz.members.api.provider;

import com.fansz.members.api.UserApi;
import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.service.ProfileService;
<<<<<<< HEAD
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.param.NullResult;
import com.fansz.members.model.param.UserInfoResult;
import com.fansz.members.model.user.ModifyProfileParam;
import com.fansz.members.model.user.QueryUserParam;
import org.springframework.beans.BeanUtils;
=======
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.StringUtils;
import com.fansz.members.model.param.ModifyProfilePara;
import com.fansz.members.model.param.UsersInfoParam;
>>>>>>> 959ccc6f288736da7023f3bbfd307fdceab3a9c3
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Vector;

/**
 * Created by root on 15-11-3.
 */
@Component("/profileProvider")
public class ProfileProvider implements UserApi {


    @Autowired
    private ProfileService profileService;

    @Override
    public CommonResult<UserInfoResult> getProfile(QueryUserParam queryUserParam) {
        CommonResult<UserInfoResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
            UserEntity user = profileService.getProfile(queryUserParam.getUid());
            UserInfoResult userInfoResult = new UserInfoResult();
            BeanUtils.copyProperties(user, userInfoResult);

        } catch (Exception iae) {
            result.setStatus("1");
        }
        return result;
    }

    @Override
    public CommonResult<NullResult> modifyProfile(ModifyProfileParam modifyProfileParam) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");
        try {

            profileService.modifyProfile(modifyProfileParam.getUid(), modifyProfileParam);
        } catch (Exception e) {
            result.setStatus("1");
        }
        return result;
    }

    /**
     * 设置用户图像
     *
     * @param form 个人头像信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<NullResult> setAvatar(InputStream form) {
        CommonResult<NullResult> result = new CommonResult<>();
        result.setStatus("0");
        try {
             //TODO

        } catch (IllegalArgumentException iae) {
            result.setStatus("1");
        }
        return result;
    }
}
