package com.fansz.service.api.provider;

import com.fansz.common.provider.AbstractProvider;
import com.fansz.common.provider.model.CommonPagedResult;
import com.fansz.common.provider.model.CommonResult;
import com.fansz.common.provider.model.NullResult;
import com.fansz.service.api.CommentApi;
import com.fansz.service.api.service.CommentService;
import com.fansz.service.exception.ApplicationException;
import com.fansz.service.model.comment.DelCommentParam;
import com.fansz.service.model.comment.PostCommentQueryParam;
import com.fansz.service.model.comment.AddCommentParam;
import com.fansz.service.model.comment.PostCommentQueryResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


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
    public CommonResult<PostCommentQueryResult> addPostComment(AddCommentParam commentPara) {
        return renderSuccess(commentService.addComment(commentPara));
    }

    /**
     * 回复评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<PostCommentQueryResult> replyComment(AddCommentParam commentPara) {
        return this.addPostComment(commentPara);
    }

    /**
     * 删除评论接口
     *
     * @param delCommentParam 评论id
     * @return resp 返回对象
     */
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
    public CommonPagedResult<PostCommentQueryResult> getCommentsByPostidFromFandom(PostCommentQueryParam commentQueryFromFandom) {
        PageBounds pageBounds = new PageBounds(commentQueryFromFandom.getPageNum(), commentQueryFromFandom.getPageSize());
        return renderPagedSuccess(commentService.getCommentsByPostidFromFandom(commentQueryFromFandom, pageBounds));
    }

    /**
     * 根据postId查询newsfeed的评论列表
     *
     * @param commentQueryFromFandom
     * @return
     * @throws ApplicationException
     */
    public CommonPagedResult<List<PostCommentQueryResult>> getCommentsByPostidFromNewsfeed(PostCommentQueryParam commentQueryFromFandom) {
        //TODO:下个版本实现
        return null;
    }

}
