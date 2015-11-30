package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserRelationEntity;
import com.fansz.members.model.profile.UserInfoResult;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserRelationEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserRelationEntity record);

    int insertSelective(UserRelationEntity record);

    UserRelationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelationEntity record);

    int updateByPrimaryKey(UserRelationEntity record);

    List<UserInfoResult> findFriends(Long userId,PageBounds pageBounds);

}