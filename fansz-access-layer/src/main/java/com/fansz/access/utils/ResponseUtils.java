package com.fansz.access.utils;

import com.fansz.common.provider.constant.ErrorCode;

/**
 * Created by allan on 15/12/15.
 */
public final class ResponseUtils {
    private final static String ERROR = "{\"status\": \"%s\",\"message\": \"%s\", \"result\": {}}";

    public static String renderMethodNameError() {
        return String.format(ERROR, "10001", "Method name error");
    }

    public static String renderAccessTokenError() {
        return String.format(ERROR, ErrorCode.ACCESS_TOKEN_INVALID.getCode(), ErrorCode.ACCESS_TOKEN_INVALID.getName());
    }

    public static String renderAppError() {
        return String.format(ERROR, ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getName());
    }

    public static String renderParamError() {
        return String.format(ERROR, ErrorCode.PARAMETERS_ERROR.getCode(), ErrorCode.PARAMETERS_ERROR.getName());
    }

    public static String renderLogicError(String errorCode, String message) {
        return String.format(ERROR, errorCode, message);
    }

}
