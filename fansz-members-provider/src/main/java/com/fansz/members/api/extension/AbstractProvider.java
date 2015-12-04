package com.fansz.members.api.extension;

import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.PageParam;
import com.fansz.members.tools.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Provider抽象类
 */
public abstract class AbstractProvider {
    protected final static NullResult PRESENCE = new NullResult();

    protected CommonResult<NullResult> renderSuccess() {
        return this.renderSuccess(PRESENCE);
    }

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

    protected <T> CommonPagedResult<T> renderPagedSuccess(PageList<T> data, String message) {
        PageParam pager = new PageParam();
        if(data.getPaginator()!=null) {
            pager.setLimit(data.getPaginator().getLimit());
            pager.setOffset(data.getPaginator().getPage());
            pager.setTotalNum(data.getPaginator().getTotalCount());
        }
        CommonPagedResult<T> result = new CommonPagedResult<>(Constants.SUCCESS,message,pager,data);
        return result;
    }

    protected <T> CommonPagedResult<T> renderPagedSuccess(PageList<T> data) {
        return renderPagedSuccess(data, "Success");
    }

}
