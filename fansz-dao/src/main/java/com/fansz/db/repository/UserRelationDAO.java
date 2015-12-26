package com.fansz.db.repository;

import com.fansz.db.entity.UserRelationEntity;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedQuery;

import java.util.List;

/**
 * Created by allan on 15/12/26.
 */
@DAO("userRelationDAO")
public interface UserRelationDAO extends IBaseDAO<UserRelationEntity> {
    @NamedQuery(queryId = "relation.findMyFriends", parameters = "myMemberSn")
    List<UserRelationEntity> findMyFriends(String myMemberSn);
}
