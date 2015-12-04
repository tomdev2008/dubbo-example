package com.fansz.members.model;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
