package com.fansz.members.consumer.model;

/**
 * Created by allan on 15/12/5.
 */
public class HttpResponseModel {

    private HttpRequestHeader heaer;

    private HttpResponseBody[] response;

    public HttpRequestHeader getHeaer() {
        return heaer;
    }

    public void setHeaer(HttpRequestHeader heaer) {
        this.heaer = heaer;
    }

    public HttpResponseBody[] getResponse() {
        return response;
    }

    public void setResponse(HttpResponseBody[] response) {
        this.response = response;
    }
}
