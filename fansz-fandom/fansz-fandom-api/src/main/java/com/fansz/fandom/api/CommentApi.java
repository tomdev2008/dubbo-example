package com.fansz.fandom.api;

import com.fansz.common.provider.annotation.DubboxMethod;
import com.fansz.common.provider.annotation.DubboxService;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.model.comment.AddCommentParam;
import com.fansz.fandom.model.comment.DelCommentParam;
import com.fansz.fandom.model.comment.PostCommentQueryParam;
import com.fansz.fandom.model.comment.PostCommentQueryResult;

/**
 * Created by root on 15-11-26.
 */
@DubboxService("comments")
public interface CommentApi {

    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @DubboxMethod("commentPost")
    CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara) throws ApplicationException;

    @DubboxMethod("replyComment")
    CommonResult<PostCommentQueryResult> replyComment(AddCommentParam commentPara) throws ApplicationException;

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @DubboxMethod("deleteComment")
    CommonResult<NullResult> removeCommet(DelCommentParam delCommentParam) throws ApplicationException;

    /**
     * 查询帖子下所有的评论列表
     *
     * @param commentQueryFromFandom
     * @return
     */
    @DubboxMethod("listPostComments")
    CommonPagedResult<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom) throws ApplicationException;

}
