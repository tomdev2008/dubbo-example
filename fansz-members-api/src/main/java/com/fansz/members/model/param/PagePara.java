package com.fansz.members.model.param;


/**
 * Created by root on 15-11-10.
 */
public class PagePara {

    private int limit;

    private int offset;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
