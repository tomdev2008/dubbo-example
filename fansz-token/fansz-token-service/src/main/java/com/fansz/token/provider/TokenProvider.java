package com.fansz.token.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.token.api.TokenApi;
import com.fansz.token.model.IMTokenParam;
import com.fansz.token.model.OssTokenParam;
import com.fansz.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by allan on 16/1/20.
 */
@Component("tokenProvider")
public class TokenProvider extends AbstractProvider implements TokenApi {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取阿里云OSS Token
     * @param ossTokenParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<Map<String, String>> getToken(OssTokenParam ossTokenParam) throws ApplicationException {
        return super.renderSuccess(tokenService.getToken());
    }

    /**
     * 获取IM Token
     * @param imTokenParam
     * @return
     * @throws ApplicationException
     */
    @Override
    public CommonResult<Map<String, String>> getIMToken(IMTokenParam imTokenParam) throws ApplicationException {
        Map<String, String> tokenMap = tokenService.requestIMToken(imTokenParam.getAppKey(), imTokenParam.getCurrentSn());
        return renderSuccess(tokenMap);
    }
}
