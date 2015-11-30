package com.fansz.members.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by allan on 15/11/30.
 */
public class CommonPagedResult<T> extends CommonResult {

    @JsonProperty("total_num")
    private Integer totalNum;

    @JsonProperty("next_curson")
    private Integer nextCursor;

    @JsonProperty("previous_cursor")
    private Integer previousCursor;

    public Integer getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(Integer previousCursor) {
        this.previousCursor = previousCursor;
    }

    public Integer getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(Integer nextCursor) {
        this.nextCursor = nextCursor;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
