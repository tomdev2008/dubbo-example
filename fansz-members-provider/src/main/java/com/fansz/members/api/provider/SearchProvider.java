package com.fansz.members.api.provider;

import com.fansz.members.api.SearchApi;
import com.fansz.members.api.service.SearchService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.comment.SearchParam;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchProvider implements SearchApi{
    @Autowired
    private SearchService searchService;

    @Override
    public CommonResult<PageList> keywordSearch(SearchParam searchParam) {
        PageBounds pageBounds = new PageBounds(searchParam.getOffset(), searchParam.getLimit());
        return searchService.keywordSearch(searchParam,pageBounds);
    }
}
