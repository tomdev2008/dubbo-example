package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserRelationEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserRelationEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRelationEntity record);

    int insertSelective(UserRelationEntity record);

    UserRelationEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRelationEntity record);

    int updateByPrimaryKey(UserRelationEntity record);

}