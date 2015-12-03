package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity;
import com.fansz.members.api.entity.UserRelationEntity;
import com.fansz.members.model.profile.ContactInfoResult;
import com.fansz.members.model.profile.ContactQueryParam;
import com.fansz.members.model.profile.UserInfoResult;
import com.fansz.members.model.relationship.FriendInfoResult;
import com.fansz.members.model.relationship.FriendsQueryParam;
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

    UserRelationEntity findRelationBySns(@Param("memberSn")String memberSn,@Param("friendSn")String friendSn);

    PageList<FriendInfoResult> findFriends(@Param("myMemberSn") String userSn, PageBounds pageBounds);

    PageList<ContactInfoResult> findRelationByMobiles(ContactQueryParam contactQueryParam, PageBounds pageBounds);

    List<UserEntity> getRelationPerson(FriendsQueryParam param);
}