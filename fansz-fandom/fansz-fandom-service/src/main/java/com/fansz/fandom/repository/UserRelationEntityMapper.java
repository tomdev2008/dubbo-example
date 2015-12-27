package com.fansz.fandom.repository;

import com.fansz.fandom.entity.UserRelationEntity;
import com.fansz.fandom.model.profile.ContactInfoResult;
import com.fansz.fandom.model.relationship.FriendInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserRelationEntityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRelationEntity record);

    int insertSelective(UserRelationEntity record);

    UserRelationEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRelationEntity record);

    int updateByPrimaryKey(UserRelationEntity record);

    /**
     * 查询用户是否是好友
     *
     * @param memberSn
     * @param friendSn
     * @return
     */
    UserRelationEntity findFriendRelationBySns(@Param("memberSn") String memberSn, @Param("friendSn") String friendSn);

    UserRelationEntity findRelation(@Param("memberSn") String memberSn, @Param("friendSn") String friendSn);

    PageList<FriendInfoResult> findFriends(@Param("myMemberSn") String userSn, PageBounds pageBounds);

    PageList<FriendInfoResult> findSpecialFriends(@Param("myMemberSn") String userSn, PageBounds pageBounds);

    PageList<ContactInfoResult> findRelationByMobiles(@Param("memberSn") String memberSn, @Param("mobileList") List<String> mobileList, PageBounds pageBounds);

    PageList<FriendInfoResult> listAddMeRequest(String memberSn, PageBounds pageBounds);

    PageList<FriendInfoResult> listMySendRequest(String memberSn, PageBounds pageBounds);
}