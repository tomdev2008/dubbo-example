package com.fansz.service.consumer.rpc;

/**
 * Created by allan on 15/12/5.
 */
public interface RpcInvoker {

    String invoke(String url, String requestBody);

}
