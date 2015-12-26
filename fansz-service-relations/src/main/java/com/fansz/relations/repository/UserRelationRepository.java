package com.fansz.relations.repository;

import com.fansz.orm.dao.IBaseDAO;
import com.fansz.orm.dao.annotation.DAO;
import com.fansz.orm.dao.annotation.NamedExec;
import com.fansz.orm.dao.annotation.NamedQuery;
import com.fansz.pub.model.Page;
import com.fansz.pub.model.QueryResult;
import com.fansz.relations.entity.UserRelationEntity;
import com.fansz.relations.model.ContactInfoResult;
import com.fansz.relations.model.FriendInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by allan on 15/12/24.
 */
@DAO("userRelationRepository")
public interface UserRelationRepository extends IBaseDAO<UserRelationEntity> {

    @NamedExec(execId = "relation.updateRelationStatus",parameters = {"id","relationStatus"})
    int updateRelationStatus(Long id,String relationStatus);

    @NamedQuery(queryId = "relation.findFriendRelationBySns", parameters = {"memberSn", "friendSn"})
    UserRelationEntity findFriendRelationBySns(String memberSn, String friendSn);

    @NamedQuery(queryId = "relation.findRelation", parameters = {"memberSn", "friendSn"})
    UserRelationEntity findRelation(String memberSn, String friendSn);

    @NamedQuery(queryId = "relation.findFriends", parameters = {"page", "myMemberSn"})
    QueryResult<FriendInfoResult> findFriends(Page page, @Param("myMemberSn") String userSn);

    @NamedQuery(queryId = "relation.findSpecialFriends", parameters = {"page", "myMemberSn"})
    QueryResult<FriendInfoResult> findSpecialFriends(Page page, String userSn);

    @NamedQuery(queryId = "relation.findRelationByMobiles", parameters = {"memberSn", "mobileList"})
    QueryResult<ContactInfoResult> findRelationByMobiles(Page page, String memberSn, List<String> mobileList);

    @NamedQuery(queryId = "relation.listAddMeRequest", parameters = {"page", "memberSn"})
    QueryResult<FriendInfoResult> listAddMeRequest(Page page, String memberSn);

    @NamedQuery(queryId = "relation.listMySendRequest", parameters = {"page", "memberSn"})
    QueryResult<FriendInfoResult> listMySendRequest(Page page, String memberSn);
}
