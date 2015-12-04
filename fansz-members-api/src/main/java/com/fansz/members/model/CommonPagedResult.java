package com.fansz.members.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by allan on 15/11/30.
 */
public class CommonPagedResult<T> extends CommonResult {

    private PageParam pager;

    public PageParam getPager() {
        return pager;
    }

    public void setPager(PageParam pager) {
        this.pager = pager;
    }
}
