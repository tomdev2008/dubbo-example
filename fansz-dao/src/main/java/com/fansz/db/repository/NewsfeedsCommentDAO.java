package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;

/**
 * Created by dell on 2015/12/28.
 */
@DAO("newsfeedsCommentDAO")
public interface NewsfeedsCommentDAO  extends IBaseDAO<NewsfeedsPostComment> {

    @NamedExec(execId = "newsfeeds.removeCommetByPostId", parameters = "postId")
    int removeCommetByPostId(Long postId);

}
