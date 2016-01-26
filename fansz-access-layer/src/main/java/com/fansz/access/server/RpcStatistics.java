package com.fansz.access.server;

import java.io.Serializable;

/**
 * Created by allan on 16/1/26.
 */
public class RpcStatistics implements Serializable {
    private static final long serialVersionUID = -8359708607419788436L;

    private Long received = null;

    public RpcStatistics(Long received) {
        this.received = received;
    }

    public RpcStatistics() {
    }

    public Long getReceived() {
        return received;
    }

    public void setReceived(Long received) {
        this.received = received;
    }
}
