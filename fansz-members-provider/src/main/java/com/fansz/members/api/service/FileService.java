package com.fansz.members.api.service;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件服务类
 *
 * Created by xuzhen on 15/7/28.
 */

public interface FileService {

    String fileUpload(
            String filePath,
            InputStream fileInputStream,
            String fileName) throws IOException;

    File getFile(String fullFileName) throws FileNotFoundException;

    File getFile(String picturePath, String fileId, int width, int height) throws IOException, MetadataException, ImageProcessingException;
}
