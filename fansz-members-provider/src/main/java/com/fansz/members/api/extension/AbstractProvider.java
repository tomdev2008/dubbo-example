package com.fansz.members.api.extension;

import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.PageParam;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Provider抽象类
 */
public abstract class AbstractProvider {
    protected final static NullResult PRESENCE = new NullResult();

    protected <T> CommonResult<T> renderSuccess(T data) {
        return renderSuccess(data, "Success");
    }

    protected <T> CommonResult<T> renderSuccess(T data, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.setResult(data);
        result.setStatus(Constants.SUCCESS);
        result.setMessage(message);
        return result;
    }

    protected <T> CommonPagedResult<List<T>> renderPagedSuccess(PageList<T> data, String message) {
        CommonPagedResult<List<T>> result = new CommonPagedResult<>();
        result.setResult(data);
        result.setStatus(Constants.SUCCESS);
        result.setMessage(message);
        PageParam pager = new PageParam();
        pager.setLimit(data.getPaginator().getLimit());
        pager.setOffset(data.getPaginator().getPage());
        result.setPager(pager);
        return result;
    }

    protected <T> CommonPagedResult<List<T>> renderPagedSuccess(PageList<T> data) {
        return renderPagedSuccess(data, "Success");
    }

}
