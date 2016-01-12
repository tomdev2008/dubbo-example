package com.fansz.push.server.model;

import java.io.Serializable;

public class JsonRequest implements Serializable {
    private static final long serialVersionUID = -4553733055168862681L;

    private RequestHeader header;

    private String body;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
