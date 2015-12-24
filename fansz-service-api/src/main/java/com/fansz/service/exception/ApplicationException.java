package com.fansz.service.exception;

/**
 * Created by allan on 15/11/27.
 */
public class ApplicationException extends RuntimeException {

    private String code;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    protected ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }
}
