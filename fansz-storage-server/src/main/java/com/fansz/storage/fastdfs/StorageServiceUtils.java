package com.fansz.storage.fastdfs;

import com.fansz.storage.config.StorageConfig;
import com.fansz.storage.model.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by allan on 15/12/1.
 */
public class StorageServiceUtils implements StorageConfig, Serializable {

    private static Logger logger = LoggerFactory.getLogger(StorageServiceUtils.class);

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;


    public static void init(String baseDir){
        try {

            String fdfsClientConfigFilePath = baseDir+"/conf/"  + CLIENT_CONFIG_FILE;
            System.out.println("loading fastdfs configuration from "+fdfsClientConfigFilePath);
            logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);
            ClientGlobal.init(fdfsClientConfigFilePath);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (Exception e) {
            logger.error("initial fastdfs client error", e);

        }
    }

    public static String upload(FastDFSFile file) {
        logger.info("File Name: " + file.getName() + "		File Length: " + file.getContent().length);

        NameValuePair[] meta_list = new NameValuePair[2];
        meta_list[0] = new NameValuePair("real_file_name", file.getName());
        meta_list[1] = new NameValuePair("size", String.valueOf(file.getContent().length));

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
        }
        logger.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

        if (uploadResults == null) {
            logger.error("upload file fail, error code: " + storageClient.getErrorCode());
        }

        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        String fileAbsolutePath = PROTOCOL + trackerServer.getInetSocketAddress().getHostName()
                + ":"
                + TRACKER_NGNIX_PORT
                + SEPARATOR
                + groupName
                + SEPARATOR
                + remoteFileName;


        logger.info("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:"
                + " " + remoteFileName);

        return fileAbsolutePath;

    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName) {
        try {
            storageClient.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("fail to delete file", e);
        }
    }

    public static StorageServer[] getStoreStorages(String groupName) {
        try {
            return trackerClient.getStoreStorages(trackerServer, groupName);
        } catch (IOException e) {
            logger.error("fail to get storage server", e);
        }
        return null;
    }

    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) {
        try {
            return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("fail to get fetch server", e);
        }
        return null;
    }
}
