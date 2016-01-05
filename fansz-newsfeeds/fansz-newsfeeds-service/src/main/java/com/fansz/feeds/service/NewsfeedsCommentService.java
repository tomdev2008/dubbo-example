package com.fansz.feeds.service;

import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.newsfeeds.model.comment.DelCommentParam;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;
import com.fansz.newsfeeds.model.comment.PostCommentQueryResult;

/**
 * Created by dell on 2015/12/28.
 */
public interface NewsfeedsCommentService {

    NewsfeedsPostComment savePostComment(NewsfeedsCommentParam commentPara);

    String deleteCommet(DelCommentParam delCommentParam);

    /**
     * 删除某个post下的所有评论
     * @param postId
     * @return
     */
    String deleteCommetByPostId(Long postId);

    /**
     * 评论朋友圈组装数据
     * @param newsfeedsPostComment
     * @return
     */
    PostCommentQueryResult assemblyPostCommentResult(NewsfeedsPostComment newsfeedsPostComment);

}