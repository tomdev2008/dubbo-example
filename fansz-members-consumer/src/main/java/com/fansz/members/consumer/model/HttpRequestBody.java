package com.fansz.members.consumer.model;

import java.util.Map;

/**
 * Created by allan on 15/12/5.
 */
public class HttpRequestBody {

    private String method;

    private Map<String,String> params;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
