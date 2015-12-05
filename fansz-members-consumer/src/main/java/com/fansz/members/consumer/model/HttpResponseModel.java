package com.fansz.members.consumer.model;

import java.util.List;

/**
 * Created by allan on 15/12/5.
 */
public class HttpResponseModel {

    private HttpRequestHeader header;

    private List<Object> response;

    public HttpRequestHeader getHeader() {
        return header;
    }

    public void setHeader(HttpRequestHeader header) {
        this.header = header;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }
}
