package com.fansz.auth.provider;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.fansz.auth.api.OssApi;
import com.fansz.auth.model.LoginResult;
import com.fansz.auth.model.OssTokenParam;
import com.fansz.auth.model.RefreshTokenParam;
import com.fansz.auth.service.OssService;
import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by allan on 16/1/4.
 */
@Component("ossProvider")
public class OssProvider extends AbstractProvider implements OssApi {

    @Autowired
    private OssService ossService;

    @Override
    public CommonResult<Map<String, String>> getToken(OssTokenParam ossTokenParam) throws ApplicationException {
        return super.renderSuccess(ossService.getToken());
    }

}
