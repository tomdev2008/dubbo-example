package com.fansz.storage.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by allan on 15/12/7.
 */
public class UploadResponse  implements Serializable{

    private static final long serialVersionUID = 4198632693376618149L;

    private String status;

    private String message;

    private List<UploadResult> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UploadResult> getResult() {
        return result;
    }

    public void setResult(List<UploadResult> result) {
        this.result = result;
    }
}
