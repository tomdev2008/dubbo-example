package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsMemberLike;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;


/**
 * Created by dell on 2015/12/29.
 */
@DAO("newsfeedsMemberLikeDAO")
public interface NewsfeedsMemberLikeDAO extends IBaseDAO<NewsfeedsMemberLike> {

    @NamedQuery(queryId = "newsfeeds.isLiked", parameters = {"memberSn","postId"})
    int isLiked(String memberSn,Long postId);

    @NamedExec(execId = "newsfeeds.removeLikeByPostId",parameters = "postId")
    int removeLikeByPostId(Long postId);

}
