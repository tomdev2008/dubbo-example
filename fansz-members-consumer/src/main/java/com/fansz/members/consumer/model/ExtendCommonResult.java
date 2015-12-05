package com.fansz.members.consumer.model;

import com.fansz.members.model.CommonResult;

/**
 * Created by allan on 15/12/5.
 */
public class ExtendCommonResult<T> extends CommonResult {
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
