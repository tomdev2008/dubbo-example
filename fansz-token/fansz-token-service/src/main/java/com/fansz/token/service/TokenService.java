package com.fansz.token.service;

import com.fansz.common.provider.exception.ApplicationException;

import java.util.Map;

/**
 * Created by allan on 16/1/4.
 */
public interface TokenService {
    Map<String, String> getToken();

    Map<String, String> requestIMToken(String appKey, String memberSn) throws ApplicationException;
}
