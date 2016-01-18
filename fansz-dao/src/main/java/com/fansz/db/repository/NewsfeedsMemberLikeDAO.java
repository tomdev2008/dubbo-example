package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsMemberLike;
import com.fansz.db.model.NewsfeedsMemberLikeVO;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;

import java.util.List;
import java.util.Map;


/**
 * Created by dell on 2015/12/29.
 */
@DAO("newsfeedsMemberLikeDAO")
public interface NewsfeedsMemberLikeDAO extends IBaseDAO<NewsfeedsMemberLike> {

    @NamedQuery(queryId = "newsfeeds.isLiked", parameters = {"memberSn","postId"})
    Map<String,Object> isLiked(String memberSn, Long postId);

    @NamedExec(execId = "newsfeeds.removeLikeByPostId",parameters = "postId")
    int removeLikeByPostId(Long postId);
    /**
     * 根据postIds,memberSn查询所有postID的memberSn好友的赞
     * @param postIds
     * @param memberSn
     * @return
     */
    @NamedQuery(queryId = "newsfeedsLike.findLikesByPostIdsAndMemberSn", parameters = {"postIds", "memberSn"})
    List<NewsfeedsMemberLikeVO> findLikesByPostIdsAndMemberSn(List<String> postIds, String memberSn);

}
