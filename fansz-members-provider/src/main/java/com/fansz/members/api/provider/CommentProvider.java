package com.fansz.members.api.provider;

import com.fansz.members.api.CommentApi;
import com.fansz.members.api.extension.AbstractProvider;
import com.fansz.members.api.service.CommentService;
import com.fansz.members.exception.ApplicationException;
import com.fansz.members.model.CommonPagedResult;
import com.fansz.members.model.CommonResult;
import com.fansz.members.model.NullResult;
import com.fansz.members.model.comment.CommentDelParam;
import com.fansz.members.model.comment.CommentParam;
import com.fansz.members.model.comment.CommentFromFandomQueryParam;
import com.fansz.members.model.comment.CommentQueryFromFandomResult;
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
    public CommonResult<CommentQueryFromFandomResult> addPostComment(CommentParam commentPara) {
        return renderSuccess(commentService.addComment(commentPara));
    }

    /**
     * 回复评论接口
     *
     * @param commentPara 评论信息
     * @return resp 返回对象
     */
    public CommonResult<CommentQueryFromFandomResult> replyComment(CommentParam commentPara) {
        return this.addPostComment(commentPara);
    }

    /**
     * 删除评论接口
     *
     * @param commentDelParam 评论id
     * @return resp 返回对象
     */
    public CommonResult<NullResult> removeCommet(CommentDelParam commentDelParam) {
        commentService.removeComment(commentDelParam);
        return renderSuccess();
    }

    /**
     * 根据postId查询fandom的评论列表
     *
     * @param commentQueryFromFandom
     * @return
     */
    public CommonPagedResult<CommentQueryFromFandomResult> getCommentsByPostidFromFandom(CommentFromFandomQueryParam commentQueryFromFandom) {
        PageBounds pageBounds = new PageBounds(commentQueryFromFandom.getOffset(), commentQueryFromFandom.getLimit());
        return renderPagedSuccess(commentService.getCommentsByPostidFromFandom(commentQueryFromFandom, pageBounds));
    }

    /**
     * 根据postId查询newsfeed的评论列表
     *
     * @param commentQueryFromFandom
     * @return
     * @throws ApplicationException
     */
    public CommonPagedResult<List<CommentQueryFromFandomResult>> getCommentsByPostidFromNewsfeed(CommentFromFandomQueryParam commentQueryFromFandom) {
        //TODO:下个版本实现
        return null;
    }

}
