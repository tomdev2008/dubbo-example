package com.fansz.token.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.token.model.IMTokenParam;
import com.fansz.token.model.OssTokenParam;

import java.util.Map;

/**
 * Created by allan on 16/1/20.
 */
@DubboxService("tokens")
public interface TokenApi {
    @DubboxMethod("getIMToken")
    CommonResult<Map<String, String>> getIMToken(IMTokenParam imTokenParam) throws ApplicationException;

    @DubboxMethod("getOssToken")
    CommonResult<Map<String,String>> getToken(OssTokenParam ossTokenParam) throws ApplicationException;
}
