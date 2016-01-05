package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.model.NewsFeedsFandomPostVO;
import com.fansz.db.model.NewsfeedsPostVO;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedQuery;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;

import java.util.List;

/**
 * Created by allan on 15/12/25.
 */
@DAO("newsfeedsPostDAO")
public interface NewsfeedsPostDAO extends IBaseDAO<NewsfeedsPost> {

    /**
     * 根据post id查找所有的post
     *
     * @param postIds
     * @return
     */
    @NamedQuery(queryId = "newsfeedsPost.findNewsfeedsPostByIds", parameters = {"postIds"})
    List<NewsfeedsPostVO> findNewsfeedsPostByIds(List<String> postIds);

    @NamedQuery(queryId = "newsfeedsPost.findNewsfeedsPostBySn", parameters = {"page", "memberSn"})
    QueryResult<NewsfeedsPost> findNewsfeedsPostBySn(Page page, String memberSn);

    @NamedQuery(queryId = "newsfeedsPost.findNewsfeedsPostByPushPostMemberSn", parameters = {"page", "memberSn"})
    QueryResult<NewsfeedsPost> findNewsfeedsPostByPushPostMemberSn(Page page, String memberSn);

    @NamedQuery(queryId = "newsfeedsPost.findFandomPostInfo", parameters = {"postIds"})
    List<NewsFeedsFandomPostVO> findNewsfeedsFandomPostInfoByPostId(List<String> postIds);
}
