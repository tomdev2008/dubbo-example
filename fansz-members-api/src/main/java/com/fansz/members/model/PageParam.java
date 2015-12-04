package com.fansz.members.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 分页参数
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = 2149367388124720438L;

    @JsonProperty("page_size")
    private Integer limit;

    @JsonProperty("page_num")
    private Integer offset;

    @JsonProperty("total_num")
    private Integer totalNum;


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
