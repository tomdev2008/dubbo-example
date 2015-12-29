package com.fansz.auth.model;

import com.fansz.common.provider.model.PageParam;
import com.fansz.fandom.model.post.PostInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by allan on 15/12/28.
 */
public class TestKyro {
    public static void main(String[] args) {
            PageParam pager = new PageParam();
            pager.setPageSize(10);
            pager.setPageNum(2);
            pager.setTotalNum(21L);
        PageList<PostInfoResult> a=new PageList<>();
        //CommonPagedResult result = new CommonPagedResult("0", "Success", pager, data);
    }
}
