package com.fansz.db.repository;

import com.fansz.db.entity.PushPost;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedQuery;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;

/**
 * Created by allan on 15/12/26.
 */
@DAO("pushPostDAO")
public interface PushPostDAO extends IBaseDAO<PushPost> {

    @NamedQuery(queryId = "pushPost.findPushPostByMemberSn", parameters = { "page", "memberSn"})
    QueryResult<PushPost> findPushPostByMemberSn(Page page, String memberSn);

}
