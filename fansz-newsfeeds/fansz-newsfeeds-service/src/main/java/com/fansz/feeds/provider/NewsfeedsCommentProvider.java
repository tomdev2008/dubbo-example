package com.fansz.feeds.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.feeds.service.NewsfeedsCommentService;
import com.fansz.newsfeeds.api.NewsfeedsCommentApi;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2015/12/28.
 */
@Component("newsfeedsCommentProvider")
public class NewsfeedsCommentProvider extends AbstractProvider implements NewsfeedsCommentApi {

    @Autowired
    private NewsfeedsCommentService newsfeedsCommentService;

    @Override
    public CommonResult<PostCommentQueryResult> addPostComment(NewsfeedsCommentParam commentPara) {
        NewsfeedsPostComment newsfeedsPostComment = newsfeedsCommentService.savePostComment(commentPara);
        PostCommentQueryResult postCommentQueryResult = newsfeedsCommentService.assemblyPostCommentResult(newsfeedsPostComment);
        return renderSuccess(postCommentQueryResult);
    }

    @Override
    public CommonResult<PostCommentQueryResult> removeCommet(DelCommentParam delCommentParam) {
        String code = newsfeedsCommentService.deleteCommet(delCommentParam);
        if (null == code) {
            PostCommentQueryResult postCommentQueryResult = new PostCommentQueryResult();
            postCommentQueryResult.setId(delCommentParam.getCommentId());
            return renderSuccess(postCommentQueryResult);
        }
        return renderFail(code);
    }
}