package com.fansz.members.api.repository;

import com.fansz.members.api.entity.UserEntity2;

public interface UserEntity2Mapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserEntity2 record);

    int insertSelective(UserEntity2 record);

    UserEntity2 selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserEntity2 record);

    int updateByPrimaryKey(UserEntity2 record);
}