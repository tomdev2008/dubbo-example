package com.fansz.members.model;

import java.io.Serializable;

/**
 * Created by allan on 15/11/22.
 */
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 4199421899377849539L;

    private String status;

    private String message;

    private int totalNum;

    private int nextCursor;

    private int previousCursor;

    private T result;

    public CommonResult(){

    }

    public CommonResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(int nextCursor) {
        this.nextCursor = nextCursor;
    }

    public int getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(int previousCursor) {
        this.previousCursor = previousCursor;
    }
}
