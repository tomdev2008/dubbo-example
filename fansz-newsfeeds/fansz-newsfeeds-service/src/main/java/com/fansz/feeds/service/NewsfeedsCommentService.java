package com.fansz.feeds.service;

import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.newsfeeds.model.comment.NewsfeedsCommentParam;

/**
 * Created by dell on 2015/12/28.
 */
public interface NewsfeedsCommentService {

    NewsfeedsPostComment savePostComment(NewsfeedsCommentParam commentPara);

    String deleteCommet(NewsfeedsCommentParam delCommentParam);

    /**
     * 删除某个post下的所有评论
     * @param postId
     * @return
     */
    String deleteCommetByPostId(Long postId);

}
