package com.fansz.auth.api;

import com.fansz.auth.model.OssTokenParam;
import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;

import java.util.Map;

/**
 * Created by allan on 16/1/4.
 */
@DubboxService("oss")
public interface OssApi {
    @DubboxMethod("getOssToken")
    CommonResult<Map<String,String>> getToken(OssTokenParam ossTokenParam) throws ApplicationException;
}
