package com.fansz.db.repository;

import com.fansz.db.entity.UserRelation;
import com.fansz.db.model.FriendInfo;
import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;

import java.util.List;

/**
 * Created by allan on 15/12/26.
 */
@DAO("userRelationDAO")
public interface UserRelationDAO extends IBaseDAO<UserRelation> {
    @NamedQuery(queryId = "relation.findMyFriends", parameters = "myMemberSn")
    List<UserRelation> findMyFriends(String myMemberSn);

    @NamedExec(execId = "relation.updateRelationStatus",parameters = {"id","relationStatus"})
    int updateRelationStatus(Long id,String relationStatus);

    @NamedQuery(queryId = "relation.findFriendRelationBySns", parameters = {"memberSn", "friendSn"})
    UserRelation findFriendRelationBySns(String memberSn, String friendSn);

    @NamedQuery(queryId = "relation.findFriendRelationBySns", parameters = {"memberSn", "friendSn"})
    UserRelation findRelation(String memberSn, String friendSn);

    @NamedQuery(queryId = "relation.findFriends", parameters = {"page", "myMemberSn"})
    QueryResult<FriendInfo> findFriends(Page page, String userSn);

    @NamedQuery(queryId = "relation.findSpecialFriends", parameters = {"page", "myMemberSn"})
    QueryResult<FriendInfo> findSpecialFriends(Page page, String userSn);

    @NamedQuery(queryId = "relation.findRelationByMobiles", parameters = {"page","memberSn", "mobileList"})
    QueryResult<FriendInfo> findRelationByMobiles(Page page, String memberSn, List<String> mobileList);

    @NamedQuery(queryId = "relation.listAddMeRequest", parameters = {"page", "memberSn"})
    QueryResult<FriendInfo> listAddMeRequest(Page page, String memberSn);

    @NamedQuery(queryId = "relation.listMySendRequest", parameters = {"page", "memberSn"})
    QueryResult<FriendInfo> listMySendRequest(Page page, String memberSn);
}
