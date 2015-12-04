package com.fansz.members.model;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by allan on 15/11/30.
 */
public class CommonPagedResult<T> implements Serializable {

    private static final long serialVersionUID = 4199421899377849539L;

    private String status;

    private String message;

    private PagedResult result;

    public CommonPagedResult() {
        result = new PagedResult();
    }

    public CommonPagedResult(String status, String message) {
        this.status = status;
        this.message = message;
        result = new PagedResult();
    }

    public CommonPagedResult(String status, String message, PageParam pager,PageList<T> data) {
        this.status = status;
        this.message = message;
        result = new PagedResult();
        result.setPager(pager);
        result.setData(data);
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


    class PagedResult {
        private PageList<T> data;
        private PageParam pager;

        public PageList<T> getData() {
            return data;
        }

        public void setData(PageList<T> data) {
            this.data = data;
        }

        public PageParam getPager() {
            return pager;
        }

        public void setPager(PageParam pager) {
            this.pager = pager;
        }
    }
}
