package com.fansz.newsfeeds.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.newsfeeds.model.comment.AddCommentParam;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;

/**
 * Created by allan on 15/12/25.
 */
@DubboxService("newsfeeds")
public interface NewsfeedsCommentApi {
    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @DubboxMethod("commentPost")
    CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara);

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @DubboxMethod("deleteComment")
    CommonResult<NullResult> removeCommet(DelCommentParam delCommentParam);
}
