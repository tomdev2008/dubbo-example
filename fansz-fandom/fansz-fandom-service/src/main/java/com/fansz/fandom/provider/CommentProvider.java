package com.fansz.fandom.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.fandom.api.CommentApi;
import com.fansz.fandom.model.comment.AddCommentParam;
import com.fansz.fandom.model.comment.DelCommentParam;
import com.fansz.fandom.model.comment.PostCommentQueryParam;
import com.fansz.fandom.model.comment.PostCommentQueryResult;
import com.fansz.fandom.service.CommentService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 评论接口类
 * Created by root on 15-11-4.
 */
@Component("commentProvider")
public class CommentProvider extends AbstractProvider implements CommentApi {

    @Autowired
    private CommentService commentService;


    /**
     * 发布帖子评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara) {
        return renderSuccess(commentService.addComment(commentPara));
    }

    /**
     * 回复评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    @Override
    public CommonResult<PostCommentQueryResult> replyComment(AddCommentParam commentPara) {
        return this.addPostComment(commentPara);
    }

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
    @Override
    public CommonResult<NullResult> removeCommet(DelCommentParam delCommentParam) {
        commentService.removeComment(delCommentParam);
        return renderSuccess();
    }

    /**
     * 根据postId查询fandom的评论列表
     *
     * @param commentQueryFromFandom
     * @return
     */
    @Override
    public CommonPagedResult<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom) {
        PageBounds pageBounds = new PageBounds(commentQueryFromFandom.getPageNum(), commentQueryFromFandom.getPageSize());
        return renderPagedSuccess(commentService.getCommentsByPostidFromFandom(commentQueryFromFandom, pageBounds));
    }


}
