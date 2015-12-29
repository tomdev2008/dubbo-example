package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsMemberLike;
import com.fansz.db.model.NewsfeedsMemberLikeVO;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedQuery;

import java.util.List;

/**
 * 朋友圈点赞dao
 * Created by wukai on 15/12/28.
 */
@DAO("newsfeedsMemberLikeDAO")
public interface NewsfeedsMemberLikeDAO extends IBaseDAO<NewsfeedsMemberLike> {
    /**
     * 根据postIds,memberSn查询所有postID的memberSn好友的赞
     * @param postIds
     * @param memberSn
     * @return
     */
    @NamedQuery(queryId = "newsfeedsLike.findLikesByPostIdsAndMemberSn", parameters = {"postIds", "memberSn"})
    List<NewsfeedsMemberLikeVO> findLikesByPostIdsAndMemberSn(List<String> postIds, String memberSn);
}
