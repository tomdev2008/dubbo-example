package com.fansz.push.server.model;

import java.io.Serializable;

public class RequestHeader implements Serializable {
    private static final long serialVersionUID = 3932697476991700953L;

    private String reqType;

    private String reqId;

    private String sn;

    private String appKey;

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
