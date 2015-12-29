package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsPostComment;
import com.fansz.db.model.NewsfeedsCommentVO;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;

import java.util.List;

/**
 * Created by dell on 2015/12/28.
 */
@DAO("newsfeedsCommentDAO")
public interface NewsfeedsCommentDAO  extends IBaseDAO<NewsfeedsPostComment> {

    @NamedExec(execId = "newsfeeds.removeCommetByPostId", parameters = "postId")
    int removeCommetByPostId(Long postId);

    /**
     * 根据postIDs, 查询memberSn能看到的comments
     * @param postIds
     * @param memberSn
     * @return
     */
    @NamedQuery(queryId = "newsfeedsComments.findCommentsByPostIdsAndMemberSn", parameters = {"postIds", "memberSn"})
    List<NewsfeedsCommentVO> findByPostIdsAndMemberSn(List<String> postIds, String memberSn);



}
