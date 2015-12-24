package com.fansz.service.consumer.utils;

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
}
