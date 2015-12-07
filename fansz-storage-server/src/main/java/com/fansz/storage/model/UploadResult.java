package com.fansz.storage.model;

import java.io.Serializable;

/**
 * Created by allan on 15/12/7.
 * <p/>
 * 文件上传结果
 */
public class UploadResult implements Serializable {

    private static final long serialVersionUID = -6184594195746924893L;

    private String fileName;

    private String url;

    public UploadResult(){

    }

    public UploadResult(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
