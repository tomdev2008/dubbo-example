package com.fansz.members.consumer.rpc;

/**
 * Created by allan on 15/12/5.
 */
public interface RpcInvoker {

    String invoke(String url, String requestBody);

}
