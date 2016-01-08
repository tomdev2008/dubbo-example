package com.fansz.redis.model;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by allan on 16/1/8.
 */
public class CountListResult<T> implements Serializable {

    private static final long serialVersionUID = 9193655766727353568L;

    private Set<T> result;

    private Long totalCount;

    public CountListResult(Set<T> result, Long totalCount) {
        this.result = result;
        this.totalCount = totalCount;
    }

    public Set<T> getResult() {
        return result;
    }

    public void setResult(Set<T> result) {
        this.result = result;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
