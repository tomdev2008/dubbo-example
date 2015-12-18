package com.fansz.storage;

import com.fansz.storage.fastdfs.StorageServiceUtils;
import com.fansz.storage.server.NettyHttpService;

/**
 * Created by allan on 15/12/1.
 */
public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("please set the baseDir");
            System.exit(0);
        }
        //String baseDir="/Users/allan/Works/backend/fansz-members/fansz-storage-server/src/main/resources/";
        String baseDir = args[0] + "/conf/";
        StorageServiceUtils.init(baseDir);
        NettyHttpService service = NettyHttpService.builder().build();
        try {
            NettyHttpService.builder().setPort(2000).setHttpChunkLimit(50 * 1024 * 1024).build().startUp();
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
