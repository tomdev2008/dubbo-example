package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.feeds.service.NewsfeedsPostService;
import com.fansz.newsfeeds.api.NewsfeedsPostApi;
import com.fansz.newsfeeds.model.post.*;
import com.fansz.pub.model.Page;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 帖子接口类
 * Created by root on 15-11-3.
 */
@Component("newsfeedsPostProvider")
public class NewsfeedsPostProvider extends AbstractProvider implements NewsfeedsPostApi {

    @Autowired
    private NewsfeedsPostService newsfeedsPostService;

    /**
     * 发帖子接口
     *
     * @param addPostParam 帖子信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<PostInfoResult> addPost(AddPostParam addPostParam)  throws ApplicationException {
        PostInfoResult postInfoResult=null;
        return renderSuccess(postInfoResult);
    }



    @Override
    public CommonResult<NullResult> removePost(RemovePostParam removePostrParam)  throws ApplicationException{
        return renderSuccess(PRESENCE);
    }

    /**
     * 获取帖子信息接口
     *
     * @param postParam 帖子
     * @return resp 返回对象
     */
    public CommonResult<PostInfoResult> getPost(GetPostByIdParam postParam)  throws ApplicationException{
        PostInfoResult postInfoResult=null;
        return renderSuccess(postInfoResult);
    }

    @Override
    public CommonPagedResult<PostInfoResult> getMyNewsfeedsList(GetPostsParam memberParam) throws ApplicationException {
        Page page = new Page();
        page.setPage(memberParam.getPageNum());
        page.setPageSize(memberParam.getPageSize());
        return renderPagedSuccess(newsfeedsPostService.findNewsfeedsListByMemberSn(memberParam.getCurrentSn(), page));
    }


}
