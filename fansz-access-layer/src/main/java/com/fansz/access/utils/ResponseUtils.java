package com.fansz.access.utils;

/**
 * Created by allan on 15/12/15.
 */
public final class ResponseUtils {
    private final static String ERROR = "{\"status\": \"%s\",\"message\": \"%s\", \"result\": {}}";

    public static String renderMethodNameError() {
        return String.format(ERROR, "10001", "Method name error");
    }

    public static String renderAccessTokenError() {
        return String.format(ERROR, "20007", "Token invalid");
    }

    public static String renderAppError() {
        return String.format(ERROR, "10001", "System error");
    }
    public static String renderParamError() {
        return String.format(ERROR, "10008", "Parameter is wrong");
    }
    public static String renderLogicError(String errorCode,String message) {
        return String.format(ERROR, errorCode, message);
    }
}
