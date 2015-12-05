package com.fansz.members.consumer.model;

/**
 * Created by allan on 15/12/5.
 */
public class HttpRequestModel {
    private HttpRequestHeader header;

    private HttpRequestBody[] request;

    public HttpRequestHeader getHeader() {
        return header;
    }

    public void setHeader(HttpRequestHeader header) {
        this.header = header;
    }

    public HttpRequestBody[] getRequest() {
        return request;
    }

    public void setRequest(HttpRequestBody[] request) {
        this.request = request;
    }
}
