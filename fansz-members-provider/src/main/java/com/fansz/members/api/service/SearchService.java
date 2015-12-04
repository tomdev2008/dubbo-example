package com.fansz.members.api.service;

import com.fansz.members.model.CommonResult;
import com.fansz.members.model.search.SearchParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by dell on 2015/12/2.
 */
public interface SearchService {

    public CommonResult<PageList> keywordSearch(SearchParam searchParam,PageBounds pageBounds);

}
