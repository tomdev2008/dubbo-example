package com.fansz.members.model;

import java.io.Serializable;

/**
 * 分页参数
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = 2149367388124720438L;

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
