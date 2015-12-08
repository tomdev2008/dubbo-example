package com.fansz.storage.model;

import java.io.Serializable;

/**
 * Created by allan on 15/12/7.
 * <p/>
 * 文件上传结果
 */
public class UploadResult implements Serializable {

    private static final long serialVersionUID = -6184594195746924893L;

    private String name;

    private String url;

    public UploadResult(){

    }

    public UploadResult(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
