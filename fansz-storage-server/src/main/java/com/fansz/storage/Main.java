package com.fansz.storage;

import com.fansz.storage.server.NettyHttpService;

/**
 * Created by allan on 15/12/1.
 */
public class Main {

    public static void main(String[] args) {
        NettyHttpService service = NettyHttpService.builder().build();
        try {
            NettyHttpService.builder().setPort(2000).build().startUp();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                service.shutDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
