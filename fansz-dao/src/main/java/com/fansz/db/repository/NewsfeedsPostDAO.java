package com.fansz.db.repository;

import com.fansz.db.entity.NewsfeedsPost;
import com.fansz.db.model.NewsfeedsPostVO;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedQuery;

import java.util.List;

/**
 * Created by allan on 15/12/25.
 */
@DAO("newsfeedsPostDAO")
public interface NewsfeedsPostDAO extends IBaseDAO<NewsfeedsPost> {

    /**
     * 根据post id查找所有的post
     * @param postIds
     * @return
     */
    @NamedQuery(queryId = "newsfeedsPost.findNewsfeedsPostByIds", parameters = {"postIds"})
    List<NewsfeedsPostVO> findNewsfeedsPostByIds(List<String> postIds);
}
