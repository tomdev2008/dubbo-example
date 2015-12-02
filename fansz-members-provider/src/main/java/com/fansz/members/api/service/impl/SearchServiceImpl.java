package com.fansz.members.api.service.impl;

import com.fansz.members.api.repository.SearchServiceMapper;
import com.fansz.members.api.service.SearchService;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.comment.SearchParam;
import com.fansz.members.model.comment.SearchResult;
import com.fansz.members.model.post.PostInfoResult;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dell on 2015/12/2.
 */
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchServiceMapper searchServiceMapper;

    @Override
    public CommonResult<PageList> keywordSearch(SearchParam searchParam,PageBounds pageBounds) {
        List<UserInfoResult> memberList =  searchServiceMapper.searchMember(searchParam.getSearchVal(),pageBounds);
        List<PostInfoResult> postList = searchServiceMapper.searchPost(searchParam.getSearchVal(),pageBounds);
        SearchResult datas = new SearchResult();
        datas.setMemberList(memberList);
        datas.setPostList(postList);
        PageList<SearchResult> list = new PageList<SearchResult>();
        list.add(datas);
        CommonResult<PageList> result  = new CommonResult<PageList>();
        result.setResult(list);
        return result;
    }

}
