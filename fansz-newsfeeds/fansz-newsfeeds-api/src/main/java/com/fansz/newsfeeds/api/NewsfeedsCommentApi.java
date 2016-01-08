package com.fansz.newsfeeds.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;

/**
 * Created by allan on 15/12/25.
 */
@DubboxService("newsfeeds")
public interface NewsfeedsCommentApi {

    /**
     * 评论朋友圈
     *
     * @param addCommentPara 评论信息
     *
     * @return resp 返回对象
     */
    @DubboxMethod("commentNewfeed")
    CommonResult<PostCommentQueryResult> addPostComment(NewsfeedsCommentParam addCommentPara) throws ApplicationException;

    /**
     * 删除朋友圈中我发表的评论
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @DubboxMethod("delMyNewsfeedComment")
    CommonResult<PostCommentQueryResult> removeCommet(DelCommentParam delCommentParam) throws ApplicationException;
}
