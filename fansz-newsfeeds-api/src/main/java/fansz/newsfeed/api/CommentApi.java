package fansz.newsfeed.api;

import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import fansz.newsfeed.api.model.comment.AddCommentParam;
import fansz.newsfeed.api.model.comment.DelCommentParam;
import fansz.newsfeed.api.model.comment.PostCommentQueryResult;

/**
 * Created by allan on 15/12/25.
 */
public interface CommentApi {
    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @DubboxService("commentPost")
    CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara);

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @DubboxService("deleteComment")
    CommonResult<NullResult> removeCommet(DelCommentParam delCommentParam);
}
